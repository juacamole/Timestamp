package ch.luethy.juan.timestamp.repository;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Integer> {
    List<Stamp> findAllByUser(User user);
}
