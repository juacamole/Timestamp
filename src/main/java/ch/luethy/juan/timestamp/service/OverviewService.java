package ch.luethy.juan.timestamp.service;

import ch.luethy.juan.timestamp.dao.Stamp;
import ch.luethy.juan.timestamp.dao.User;
import ch.luethy.juan.timestamp.repository.StampRepository;
import ch.luethy.juan.timestamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OverviewService {

    private final StampRepository stampRepository;
    private final UserRepository userRepository;

    private final StampService stampService;

    public List<Stamp> getAllStamps() {
        return stampRepository.findAll();
    }

    public List<Stamp> getStampByUserId(int id) {
        return stampRepository.findAllByUser(userRepository.findById(id).get());
    }

    public String getStatusByUserId(int id) {
        return stampService.getStatus((userRepository.findById(id).get().getUsername()));
    }

    public Map<User, String> getAllStatus() {
        List<User> users = userRepository.findAll();
        Map<User, String> statusMap = new HashMap<>();
        for (User user : users) {
            statusMap.put(user, stampService.getStatus(user.getUsername()))  ;
        }
        return statusMap;
    }

    public LocalTime getUserWorktime(int id) {
        User user = userRepository.findById(id).get();
        return stampService.calculateWorktime(user.getUsername());
    }

    public LocalTime getUserWorktimeLeft(int id) {
        User user = userRepository.findById(id).get();
        return stampService.getLeftTime(user.getUsername());
    }
}
