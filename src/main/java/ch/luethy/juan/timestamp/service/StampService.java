package ch.luethy.juan.timestamp.service;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.dto.StampDto;
import ch.luethy.juan.timestamp.repository.StampRepository;
import ch.luethy.juan.timestamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final UserRepository userRepository;


    public ResponseEntity<List<Stamp>> stamp(String username) {
        Stamp stamp = new Stamp();
        stamp.setUser(userRepository.findUserByUsername(username));
        stamp.setTime(LocalTime.now());
        stampRepository.save(stamp);
        return ResponseEntity.ok(stampRepository.findAllByUser(userRepository.findUserByUsername(username)));
    }

    public ResponseEntity<List<Stamp>> findAll(String username) {
        return ResponseEntity.ok(stampRepository.findAllByUser(userRepository.findUserByUsername(username)));
    }


    public LocalTime calculateWorktime(String username) {
        List<Stamp> stampList = stampRepository.findAllByUser(userRepository.findUserByUsername(username));
        if (stampList == null || stampList.isEmpty()) {
            return LocalTime.of(0, 0, 0, 0);
        }
        List<LocalTime> timeList = new ArrayList<>();
        for (Stamp stamp : stampList) {
            timeList.add(stamp.getTime());
        }
        if (timeList.size() % 2 == 1) {
            timeList.add(LocalTime.now());
        }

        int workHours = 0;
        int workMinutes = 0;

        for (int i = 0; i+1 < timeList.size(); i+=2) {
            LocalTime stempelDiff = timeList.get(i+1)
                    .minusHours(timeList.get(i).getHour())
                    .minusMinutes(timeList.get(i).getMinute())
                    .minusSeconds(timeList.get(i).getSecond())
                    .minusNanos(timeList.get(i).getNano());
            workHours+=stempelDiff.getHour();
            workMinutes+=stempelDiff.getMinute();
            if (workMinutes >59) {
                workMinutes = 0;
                workHours++;
            }
        }
        return LocalTime.of(workHours, workMinutes);
    }

    public LocalTime getLeftTime(String username) {
        LocalTime workTime = calculateWorktime(username);
        User user = userRepository.findUserByUsername(username);
        LocalTime neededWorkTime = LocalTime.of(user.getWorkhours(), user.getWorkminutes());
        if (neededWorkTime.minusHours(workTime.getHour()).minusMinutes(workTime.getMinute()).isAfter(neededWorkTime)) {
            return LocalTime.of(0, 0);
        } else {
            return neededWorkTime.minusHours(workTime.getHour()).minusMinutes(workTime.getMinute());
        }
    }

    public String getStatus(String username) {
        List<Stamp> stampList = stampRepository.findAllByUser(userRepository.findUserByUsername(username));
        if (stampList.size() % 2 == 0) {
            return "Geeked";
        } else {
            return "Locked in";
        }
    }


    public ResponseEntity<List<Stamp>> stampForUser(StampDto stamp) {
        Optional<User> user = userRepository.findById(stamp.getUserid());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (checkIfStampTimeIllegal(stamp)) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Stamp newStamp = new Stamp();
        newStamp.setUser(user.get());
        newStamp.setTime(LocalTime.of(stamp.getHour(), stamp.getMinute()));
        stampRepository.save(newStamp);
        return ResponseEntity.ok(stampRepository.findAllByUser(user.get()));
    }

    public ResponseEntity<List<Stamp>> deleteStamp(int stampId) {
        Optional<Stamp> stamp = stampRepository.findById(stampId);
        if (stamp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        stampRepository.delete(stamp.get());
        return ResponseEntity.ok(stampRepository.findAllByUser(stamp.get().getUser()));
    }

    public ResponseEntity<List<Stamp>> updateStamp(int id, StampDto stamp) {
        Optional<Stamp> existingStamp = stampRepository.findById(id);
        if (existingStamp.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (checkIfStampTimeIllegal(stamp)) {
            return ResponseEntity.unprocessableEntity().build();
        }
        stampRepository.deleteById(existingStamp.get().getId());
        Stamp newStamp = new Stamp();
        newStamp.setUser(existingStamp.get().getUser());
        newStamp.setTime(LocalTime.of(stamp.getHour(), stamp.getMinute()));
        stampRepository.save(newStamp);
        return ResponseEntity.ok(stampRepository.findAllByUser(newStamp.getUser()));
    }

    private boolean checkIfStampTimeIllegal(StampDto stamp) {
        return stamp.getHour() >= 24 || stamp.getMinute() >= 60;
    }
}
