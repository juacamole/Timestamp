package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/user")
@Tag(name = "User", description = "Endpunkte für die Erstellung und Bearbeitung von Benutzern")
public class UserController {

    private final UserService service;

    @GetMapping
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Alle User anzeigen", description = "Zeigt eine Liste aller User an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User mit ID anzeigen", description = "Zeigt den User mit gegebener ID an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<User> findById(@Parameter(description = "ID des Users") @PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("/firstname/{name}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User mit Vornamen anzeigen", description = "Zeigt den User mit gegebenem Vornamen an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> findByFirstName(@Parameter(description = "Vorname des Users") @PathVariable String name) {
        return service.findByFirstName(name);
    }


    @GetMapping("/lastname/{name}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User mit Nachnamen anzeigen", description = "Zeigt den User mit gegebenem Nachnamen an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> findByLastName(@Parameter(description = "Vorname des Users") @PathVariable String name) {
        return service.findByLastName(name);
    }

    @PostMapping
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User speichern", description = "Speichert einen Neuen User mit gegebenen Daten.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> save(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Userdaten ändern", description = "Ändert die Daten des Users mit gegebener ID, zu gegebenen Daten.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> update(@Parameter(description = "ID des Users") @PathVariable int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Neuer User") @Validated @RequestBody User user) {
        return service.update(user, id);
    }

    @DeleteMapping
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User löschen", description = "Löscht den gegebenen User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> delete(@Validated @RequestBody User user) {
        return service.delete(user);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User mit ID löschen", description = "Löscht den User mit gegebener ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<User>> delete(@Parameter(description = "ID des Users") @PathVariable int id) {
        return service.deleteById(id);
    }
}
