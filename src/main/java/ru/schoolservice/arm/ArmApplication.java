package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.model.Vehicle;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.repository.VehicleRepository;
import ru.schoolservice.arm.service.DbService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final DbService dbService;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        dbService.persistTester();
        //dbService.mergeTester2();
        userRepository.findAll().forEach(System.out::println);

//        System.out.println("**************************************newUser******************************************");
//        User user = new User("new_name");
//        user.addVehicle(new Vehicle("lamba", user));
//        user.addVehicle(new Vehicle("ferra", user));
//        user = userRepository.save(user);
//        System.out.println("**************************************afterSave*****************************************");
//        System.out.println(user);
//        System.out.println("**************************************allAfterSave***************************************");
//        System.out.println("**************************************merge*********************************************");
//        User user2 = userRepository.getById(2);
//        user2.setName("Вай вай");
//        user2.getList().get(0).setSerial("asdfasdfadsf");
//        user2 = userRepository.save(user2);
//        System.out.println(user2);
//        userRepository.deleteById(2);
//        vehicleRepository.findAll().forEach(System.out::println);
    }
}
