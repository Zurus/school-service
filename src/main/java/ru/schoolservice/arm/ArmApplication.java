package ru.schoolservice.arm;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.dto.CacheDto;
import ru.schoolservice.arm.dto.UserDto;
import ru.schoolservice.arm.mapper.ToEntity;
import ru.schoolservice.arm.mapper.toDto;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.Timur;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.repository.CacheRepository;
import ru.schoolservice.arm.repository.ServiceApplication;
import ru.schoolservice.arm.repository.TimurRepository;
import ru.schoolservice.arm.repository.UserRepository;
import ru.schoolservice.arm.service.CacheService;
import ru.schoolservice.arm.service.Checker;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("**************************************findAll******************************************");
        init("name", "shit", "vai", 2, 3);
        List<User> list = userRepository.findAllByTimurIdWithCaches(1);
        List<UserDto> listDtos = list.stream().map(toDto::toDto).collect(Collectors.toList());
        update_dto(listDtos);
        saveAlt(listDtos, 1);
        int i = 0;
    }


    @Transactional
    public void saveAlt(List<UserDto> userDtos, int timurId) {
        Set<Integer> dtoUserIds = userDtos.stream()
                .map(UserDto::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        //userRepository.deleteAllByIdIn(dtoUserIds);
        userRepository.deleteAllByTimurId(timurId);
        List<User> users = userDtos.stream().map(ToEntity::toEntity).collect(Collectors.toList());
        userRepository.saveAll(users);
    }


    private void init(String timurName, String userName, String cacheName, int userCount, int cacheCount) {
        Timur timur = new Timur(timurName);
        timurRepository.save(timur);
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            User user1 = new User(userName + i, timur.getId(), null);
            for (int j = 0; j < cacheCount; j++) {
                initCache(user1, cacheName, counter++);
            }
            userRepository.save(user1);
        }
    }

    public void initCache(User user, String cacheName, int count) {
        user.add(new Cache(cacheName + count, null));
    }


    public void update_dto(List<UserDto> list) {
        list.remove(0);
        UserDto userDto = new UserDto();
        userDto.setEmail("shit");
        userDto.setTimurId(list.get(0).getTimurId());
    }

    @Override
    public Cache smart(Cache cache) {
        System.out.println("**********************************************");
        //User user = cache.getUser();
        //System.out.println(user);
        return cache;
    }
}