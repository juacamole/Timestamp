package ch.kuan.timestamp.repository;

import ch.kuan.timestamp.dao.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    ResponseEntity<List<User>> findUserByFirstname(@NotEmpty @Size(min = 1, max = 20) String firstname);

    ResponseEntity<List<User>> findUserByLastname(@NotEmpty @Size(min = 1, max = 20) String lastname);
}
