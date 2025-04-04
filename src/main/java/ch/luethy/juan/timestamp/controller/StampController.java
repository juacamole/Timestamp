package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.StampService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StampController {

    private final StampService service;

    @GetMapping
    @RolesAllowed(value = Roles.USER)
    public ResponseEntity<List<Stamp>> findAll(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return service.findAll(username);
    }

    @PostMapping
    @RolesAllowed(value = Roles.USER)
    public ResponseEntity<List<Stamp>> get(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return service.stamp(username);
    }

    @GetMapping("/worktime")
    @RolesAllowed(value = Roles.USER)
    public ResponseEntity<LocalTime> getWorktime(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return ResponseEntity.ok(service.calculateWorktime(username));
    }

    @GetMapping("/worktimeleft")
    @RolesAllowed(value = Roles.USER)
    public ResponseEntity<LocalTime> getTimeLeft(Authentication auth) {
        Jwt jwt = (Jwt)  auth.getPrincipal();
        String username = jwt.getClaim("preferred_username");
        return ResponseEntity.ok(service.getLeftTime(username));
    }

}
