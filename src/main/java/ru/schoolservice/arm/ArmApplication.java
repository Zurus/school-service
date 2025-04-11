package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.Timur;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.CacheRepository;
import ru.schoolservice.arm.repository.ServiceApplication;
import ru.schoolservice.arm.repository.TimurRepository;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.service.CacheService;
import ru.schoolservice.arm.service.Checker;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class ArmApplication implements ApplicationRunner, Checker {

    private final UserRepository userRepository;
    private final CacheRepository cacheRepository;
    private final ServiceApplication application;
    private final CacheService cacheService;
    private final ServiceApplication getApplication;
    private final TimurRepository timurRepository;

    public static void main(String[] args) {
        SpringApplication.run(ArmApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("**************************************findAll******************************************");
//        User user1 = application.getUser(1);
//        System.out.println("**************************************findAll******************************************");
//        System.out.println(user1.getCache());
//        System.out.println(cache1.getUser());
//        User user1 = userRepository.findById(1).get();
//        Cache cache1 = cacheService.getCache(1);
//        Cache cache1 = cacheRepository.findById(1).get();
//        cacheService.getCache(cache1, this::smart);
        //cacheRepository.findAll();
//        System.out.println(userRepository.findByEmailIgnoreCase("last"));

        Timur timur = new Timur("asdfasdf");
        timurRepository.save(timur);


        Cache cache1 = new Cache("cache1", null);
        Cache cache2 = new Cache("cache2", null);
        Cache cache3 = new Cache("cache3", null);
        List<Cache> list = new ArrayList<>();
        list.add(cache1);
        list.add(cache2);
        list.add(cache3);

        User user1 = new User("mail", timur.getId(), list);
        userRepository.save(user1);


        User user = getApplication.getUser(1);
        int i = 0;
    }


    @Override
    public Cache smart(Cache cache) {
        System.out.println("**********************************************");
        User user = cache.getUser();
        System.out.println(user);
        return cache;
    }
}