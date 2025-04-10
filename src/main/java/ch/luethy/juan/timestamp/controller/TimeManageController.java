package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dto.StampDto;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.StampService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/manage")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class TimeManageController {

    private final StampService service;

    @PostMapping("/new")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<List<Stamp>> newStamp(@RequestBody @Validated StampDto stamp) {
        return service.stampForUser(stamp);
    }

    @DeleteMapping("/delete/{stampId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<List<Stamp>> deleteStamp(@PathVariable int stampId) {
        return service.deleteStamp(stampId);
    }

    @PutMapping("/update/{stampId}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<List<Stamp>> updateStamp(@PathVariable int stampId, @RequestBody StampDto stamp) {
        return service.updateStamp(stampId, stamp);
    }

}
