package ch.kuan.timestamp.service;

import ch.kuan.timestamp.dao.User;
import ch.kuan.timestamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
            repository.delete(foundUser.get());
            user.setId(id);
            repository.save(user);
            return ResponseEntity.ok(repository.findAll());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<User>> delete(User user) {
        repository.delete(user);
        return ResponseEntity.ok(repository.findAll());
    }
}
