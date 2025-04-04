package ch.luethy.juan.timestamp.service;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.repository.StampRepository;
import ch.luethy.juan.timestamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
            timeList.removeLast();
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
        return neededWorkTime.minusHours(workTime.getHour()).minusMinutes(workTime.getMinute());
    }

    public String getStatus(String username) {
        List<Stamp> stampList = stampRepository.findAllByUser(userRepository.findUserByUsername(username));
        if (stampList.size() % 2 == 0) {
            return "Geeked";
        } else {
            return "Locked in";
        }
    }
}
