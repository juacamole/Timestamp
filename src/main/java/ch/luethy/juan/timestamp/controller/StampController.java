package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.StampService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@Validated
@RequestMapping("/stamp")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name="Stamp", description = "Endpunkte für einfache Stempel-Logik")
public class StampController {

    private final StampService service;

    @GetMapping
    @RolesAllowed(Roles.USER)
    @Operation(summary = "Stempel anzeigen", description = "Zeigt alle Stempel des ausführenden Users an.")
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
    public ResponseEntity<List<Stamp>> findAll(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return service.findAll(username);
    }

    @PostMapping
    @RolesAllowed(Roles.USER)
    @Operation(summary = "Stempeln", description = "Erstellt einen Stempel für den ausführenden User mit der Aktuellen Zeit.")
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
    public ResponseEntity<List<Stamp>> stamp(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return service.stamp(username);
    }

    @GetMapping("/worktime")
    @RolesAllowed(Roles.USER)
    @Operation(summary = "Gearbeitete Zeit", description = "Zeigt die momentan abgearbeitete Zeit des ausführenden Users an.")
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
    public ResponseEntity<LocalTime> getWorktime(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return ResponseEntity.ok(service.calculateWorktime(username));
    }

    @GetMapping("/worktimeleft")
    @RolesAllowed(Roles.USER)
    @Operation(summary = "Zu arbeitende Zeit", description = "Zeigt die verbleibende Arbeitszeit des ausführenden Users an.")
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
    public ResponseEntity<LocalTime> getTimeLeft(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return ResponseEntity.ok(service.getLeftTime(username));
    }

    @GetMapping("/status")
    @RolesAllowed(Roles.USER)
    @Operation(summary = "Status anzeigen", description = "Zeigt den aktuellen Stempel-Status des ausführenden Users an.")
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
    public ResponseEntity<String> getStatus(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return ResponseEntity.ok(service.getStatus(username));
    }

}
