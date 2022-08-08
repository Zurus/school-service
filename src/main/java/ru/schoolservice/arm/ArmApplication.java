package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.schoolservice.arm.repository.UserRepository;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("**************************************findAll******************************************");
        System.out.println(userRepository.findByEmailIgnoreCase("last"));
    }
}
