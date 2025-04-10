package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.OverviewService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/overview")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
@Tag(name="Overview", description = "Endpunkte für die Überwachung von Benutzern")
public class OverviewController {

    private final OverviewService service;

    @GetMapping
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Alles anzeigen", description = "Zeigt alle existierenden Stempel an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = Stamp.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<Stamp>> getStamps() {
        return ResponseEntity.ok(service.getAllStamps());
    }

    @GetMapping("/user/{id}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User spezifisch anzeigen", description = "Zeigt alle existierenden Stempel des Users mit gegebener ID an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array =
                    @ArraySchema(schema = @Schema(implementation = Stamp.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<List<Stamp>> getStamp(@Parameter(description = "ID des Users der angezeigt werden soll") @Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getStampByUserId(id));
    }

    @GetMapping("/user/{id}/status")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User status anzeigen", description = "Zeigt den momentanen Stempelzustand des Users mit gegebener ID an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<String> getStatus(@Parameter(description = "ID des Users der angezeigt werden soll") @Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getStatusByUserId(id));
    }

    @GetMapping("/user/status")
    @Operation(summary = "Alle status anzeigen", description = "Zeigt den momentanen Stempelzustand aller User an.")
    @RolesAllowed(Roles.ADMIN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<Map<User, String>> getAllStatus() {
        return ResponseEntity.ok(service.getAllStatus());
    }

    @GetMapping("/user/{id}/worktime")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User arbeitszeit anzeigen", description = "Zeigt die momentan abgearbeitete Arbeitszeit des Users mit gegebener ID an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LocalTime.class))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<LocalTime> getWorkTime(@Parameter(description = "ID des Users der angezeigt werden soll") @Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getUserWorktime(id));
    }

    @GetMapping("/user/{id}/worktimeleft")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "User restzeit anzeigen", description = "Zeigt die momentane übrige Arbeitszeit des Users mit gegebener ID an.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LocalTime.class))
            }),
            @ApiResponse(responseCode = "401", description = "Bad Bearer Token", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
            @ApiResponse(responseCode = "403", description = "Needed Role not Supplied", content = {
                    @Content(schema = @Schema(hidden = true))
            }),
    })
    public ResponseEntity<LocalTime> getWorkTimeLeft(@Parameter(description = "ID des Users der angezeigt werden soll") @Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getUserWorktimeLeft(id));
    }

}
