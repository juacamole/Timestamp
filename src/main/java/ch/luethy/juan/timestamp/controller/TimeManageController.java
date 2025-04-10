package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dto.StampDto;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.StampService;
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
@RequestMapping("/manage")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name="Manage", description = "Endpunkte für bearbeitung von Stempeln")
public class TimeManageController {

    private final StampService service;

    @PostMapping("/new")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Neuen Stempel erstellen", description = "Erstellt einen neuen Stempel mit gegebenen Zeiten für gegebenen User.")
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
    public ResponseEntity<List<Stamp>> newStamp(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Stempel") @RequestBody @Validated StampDto stamp) {
        return service.stampForUser(stamp);
    }

    @DeleteMapping("/delete/{stampId}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Stempel löschen", description = "Löscht den Stempel mit gegebener ID.")
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
    public ResponseEntity<List<Stamp>> deleteStamp(@Parameter(description = "ID des Stempels") @PathVariable int stampId) {
        return service.deleteStamp(stampId);
    }

    @PutMapping("/update/{stampId}")
    @RolesAllowed(Roles.ADMIN)
    @Operation(summary = "Stempel ändern", description = "Ändert den Zeitrahmen des Stempels mit gegebener ID zu gegebenem Zeitrahmen.")
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
    public ResponseEntity<List<Stamp>> updateStamp(@Parameter(description = "ID des Stempels") @PathVariable int stampId, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Stempel") @RequestBody StampDto stamp) {
        return service.updateStamp(stampId, stamp);
    }

}
