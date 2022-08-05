package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.schoolservice.arm.model.Role;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(new User("user@gmail.com", "User_First", "User_Last", "password", Stream.of(Role.ROLE_USER).collect(Collectors.toSet())));
        userRepository.save(new User("admin@javaops.ru", "Admin_First", "Admin_Last", "admin", Stream.of(Role.ROLE_USER, Role.ROLE_ADMIN).collect(Collectors.toSet())));
        userRepository.save(new User("user2@javaops.ru", "User_second", "Name", "123", Stream.of(Role.ROLE_USER).collect(Collectors.toSet())));
        System.out.println("**************************findALL*******************************");
        System.out.println(userRepository.findAll());
    }
}
