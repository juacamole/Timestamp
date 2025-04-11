package ch.luethy.juan.timestamp.db;

import ch.luethy.juan.timestamp.TimestampApplication;
import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.repository.StampRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TimestampApplication.class)
public class StampTest {

    @Autowired
    private StampRepository stampRepository;

    @BeforeEach
    public void setUp() {
        stampRepository.deleteAll();
        stampRepository.flush();
    }

    @Test
    public void whenStampSaved_shouldReturnStamp() {
        Stamp stamp = new Stamp();
        stampRepository.save(stamp);
        Assertions.assertEquals(stamp, stampRepository.findAll().getFirst());
    }

    @Test
    public void whenStampNotSaved_shouldThrowException() {
        Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, ()->stampRepository.findAll().get(0));
    }

    @Test
    public void whenStampIsDeleted_shouldNotReturnStamp() {
        Stamp stamp = new Stamp();
        stampRepository.save(stamp);
        stampRepository.delete(stamp);
        Assertions.assertEquals(0, stampRepository.findAll().size());
    }

    @Test
    public void whenSavedWithId_ShouldBeFindableWithId() {
        Stamp stamp = new Stamp();
        stampRepository.save(stamp);
        Assertions.assertEquals(stamp, stampRepository.findById(stamp.getId()).get());
    }

}
