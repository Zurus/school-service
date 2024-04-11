package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import ru.schoolservice.arm.repository.UserRepository;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@AllArgsConstructor
@EnableCaching
public class ArmApplication implements ApplicationRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        dbService.persistTester();
//        dbService.mergeTester1();
//        dbService.mergeTester2();
//        dbService.orphanTester();
//        dbService.removeTester1();
        //dbService.removeTester2();
//        dbService.removeTester3();
//        userRepository.findAll().forEach(System.out::println);

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


//    @Bean("habrCacheManager")
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager() {
//            @Override
//            protected Cache createConcurrentMapCache(String name) {
//                return new ConcurrentMapCache(
//                        name,
//                        CacheBuilder.newBuilder()
//                                .expireAfterWrite(1, TimeUnit.SECONDS)
//                                .build().asMap(),
//                        false);
//            }
//        };
//    }
}
