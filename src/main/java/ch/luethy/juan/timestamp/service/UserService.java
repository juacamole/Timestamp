package ch.luethy.juan.timestamp.service;

import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;


    public List<User> findAll() {
        return repository.findAll();
    }

    public ResponseEntity<User> findById(int id) {
        Optional<User> foundUser = repository.findById(id);
        return foundUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<User>> findByFirstName(String name) {
        return repository.findUserByFirstname(name);
    }

    public ResponseEntity<List<User>> findByLastName(String name) {
        return repository.findUserByLastname(name);
    }

    public ResponseEntity<List<User>> save(User user) {
        repository.save(user);
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<List<User>> update(User user, int id) {
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isPresent()) {
            User existingUser = foundUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setWorkhours(user.getWorkhours());
            existingUser.setWorkminutes(user.getWorkminutes());
            repository.save(existingUser);
            return ResponseEntity.ok(repository.findAll());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<User>> delete(User user) {
        repository.delete(user);
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<List<User>> deleteById(int id) {
        repository.deleteById(id);
        return ResponseEntity.ok(repository.findAll());
    }
}
