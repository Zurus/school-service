package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.model.Vehicle;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.repository.VehicleRepository;

import javax.validation.constraints.NotNull;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private UserRepository userRepository;
    private VehicleRepository vehicleRepository;




    @Transactional
    public void orphanTester() {
        System.out.println("************************************orphan**********************************************");
        User user = userRepository.getById(1);
        user.removeVehicle(user.getList().get(0));
        vehicleRepository.findAll().forEach(System.out::println);
    }


    @Transactional
    public void removeTester2() {
        System.out.println("************************************remove**********************************************");
        User user = userRepository.getById(1);
        Vehicle vehicle = user.getList().get(0);
        user.removeVehicle(vehicle);
        vehicleRepository.delete(vehicle);
    }

    @Transactional
    public void removeTester1() {
        System.out.println("************************************remove**********************************************");
        User user = userRepository.getById(1);
        //user.removeVehicle(user.getList().get(0));
        userRepository.delete(user);
    }

    @Transactional
    public void persistTester() {
        System.out.println("************************************persist*******************************************");
        User user = new User("новое имя");
        user.addVehicle(new Vehicle("new_v1"));
        user.addVehicle(new Vehicle("new_v2"));
        userRepository.save(user);
//        User userFromRep = userRepository.getById(3);
//        System.out.println(userFromRep);
    }


    @Transactional
    public void mergeTester2() {
        System.out.println("*************************************merge*********************************************");

        User user = userRepository.getById(1);
        user.setName("Новое имя");
        user.getList().get(0).setSerial("новый серийник");
        user = userRepository.save(user);
    }

    @Transactional
    public void mergeTester1() {
        System.out.println("*************************************merge*********************************************");

        Vehicle vehicle = vehicleRepository.getById(1);
        vehicle.setSerial("new Serial");
        vehicle.getUser().setName("new name");
        vehicleRepository.save(vehicle);
    }

}
