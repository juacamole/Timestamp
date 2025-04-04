package ch.luethy.juan.timestamp.controller;


import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.OverviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
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
public class OverviewController {

    private final OverviewService service;

    @GetMapping
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<List<Stamp>> getStamps() {
        return ResponseEntity.ok(service.getAllStamps());
    }

    @GetMapping("/user/{id}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<List<Stamp>> getStamp(@Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getStampByUserId(id));
    }

    @GetMapping("/user/{id}/status")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<String> getStatus(@Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getStatusByUserId(id));
    }

    @GetMapping("/user/status")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<Map<User, String>> getAllStatus() {
        return ResponseEntity.ok(service.getAllStatus());
    }

    @GetMapping("/user/{id}/worktime")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<LocalTime> getWorkTime(@Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getUserWorktime(id));
    }

    @GetMapping("/user/{id}/worktimeleft")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<LocalTime> getWorkTimeLeft(@Validated @PathVariable int id) {
        return ResponseEntity.ok(service.getUserWorktimeLeft(id));
    }

}
