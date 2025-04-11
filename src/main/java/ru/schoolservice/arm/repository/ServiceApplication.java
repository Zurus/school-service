package ru.schoolservice.arm.repository;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;

@Service
@AllArgsConstructor
public class ServiceApplication {

    private final UserRepository userRepository;

    @Transactional
    public User getUser(Integer claimId) {
        return userRepository.findById(claimId).get();
    }


}
