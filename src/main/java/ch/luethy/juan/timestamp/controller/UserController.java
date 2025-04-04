package ch.luethy.juan.timestamp.controller;

import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.security.Roles;
import ch.luethy.juan.timestamp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @GetMapping
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<User> findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("/firstname/{name}")
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> findByFirstName(@PathVariable String name) {
        return service.findByFirstName(name);
    }


    @GetMapping("/lastname/{name}")
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> findByLastName(@PathVariable String name) {
        return service.findByLastName(name);
    }

    @PostMapping
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> save(@RequestBody User user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> update(@PathVariable int id, @Validated @RequestBody User user) {
        return service.update(user, id);
    }

    @DeleteMapping
    @RolesAllowed(value = Roles.ADMIN)
    public ResponseEntity<List<User>> delete(@Validated @RequestBody User user) {
        return service.delete(user);
    }

}
