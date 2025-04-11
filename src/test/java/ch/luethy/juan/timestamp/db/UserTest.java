package ch.luethy.juan.timestamp.db;

import ch.luethy.juan.timestamp.TimestampApplication;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TimestampApplication.class)
public class UserTest {

    @Autowired
    UserRepository userRepository;

    private final User testUser = new User();

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        initTestUser();
    }

    private void initTestUser() {
        testUser.setUsername("test");
        testUser.setFirstname("Test");
        testUser.setLastname("Test");
        testUser.setWorkhours((byte) 8);
        testUser.setWorkminutes((byte) 12);
    }

    @Test
    public void whenUserSaved_shouldReturnSavedUser() {
        userRepository.save(testUser);
        Assertions.assertEquals(testUser, userRepository.findAll().getFirst());
    }

    @Test
    public void whenUserNotSaved_shouldThrowException() {
        Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> userRepository.findAll().get(0));
    }

}
