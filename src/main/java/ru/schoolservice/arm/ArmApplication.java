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
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("**************************************findAll******************************************");
        init("name", "shit", "vai", 2, 3);
        List<User> list = userRepository.findAllByTimurId(1);
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

    @Transactional
    public void save(List<UserDto> userDtos) {
        // 1. Собираем все ID из DTO
        Set<Integer> dtoUserIds = userDtos.stream()
                .map(UserDto::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 2. Удаление: один запрос
        if (!dtoUserIds.isEmpty()) {
            userRepository.deleteAllByIdNotIn(dtoUserIds);
        } else {
            userRepository.deleteAll();
        }

        // 3. Пакетная загрузка существующих пользователей: один запрос
        Map<Integer, User> existingUsers = userRepository.findAllById(dtoUserIds)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // 4. Пакетная обработка кэшей
        processAllCaches(userDtos);

        // 5. Пакетное сохранение: несколько запросов (зависит от batch size)
        List<User> usersToSave = userDtos.stream()
                .map(dto -> convertToEntity(dto, existingUsers.get(dto.getId())))
                .collect(Collectors.toList());

        userRepository.saveAll(usersToSave);
    }

    private User convertToEntity(UserDto dto, User existing) {
        User user = existing != null ? existing : new User();
        // Обновляем поля
        user.setEmail(dto.getEmail());
        user.setTimurId(dto.getTimurId());

        // Обработка кэшей
        syncCaches(user, dto.getCaches());

        return user;
    }

    private void processAllCaches(List<UserDto> userDtos) {
        // 1. Собираем все ID кэшей
        Set<Integer> cacheIds = userDtos.stream()
                .flatMap(dto -> dto.getCaches().stream())
                .map(CacheDto::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 2. Загружаем все кэши одним запросом
        Map<Integer, Cache> existingCaches = cacheRepository.findAllById(cacheIds)
                .stream()
                .collect(Collectors.toMap(Cache::getId, Function.identity()));

        // 3. Обновляем кэши в DTO
        userDtos.forEach(dto ->
                dto.getCaches().forEach(cacheDto -> {
                    if (cacheDto.getId() != null) {
                        Cache cache = existingCaches.get(cacheDto.getId());
                        if (cache != null) {
                            cache.setCache(cacheDto.getCache());
                        }
                    }
                })
        );
    }

    private void syncCaches(User user, List<CacheDto> cacheDtos) {
        // 1. Удаляем отсутствующие кэши
        Set<Integer> dtoCacheIds = cacheDtos.stream()
                .map(CacheDto::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

//        user.getCaches().removeIf(cache ->
//                !dtoCacheIds.contains(cache.getId())
//        );

        // 2. Добавляем/обновляем кэши
//        cacheDtos.forEach(dto -> {
//            Cache cache = dto.getId() != null ?
//                    user.getCaches().stream()
//                            .filter(c -> c.getId().equals(dto.getId()))
//                            .findFirst()
//                            .orElse(new Cache()) :
//                    new Cache();
//
//            cache.setCache(dto.getCache());
//            cache.setUser(user);
//
//            if (!user.getCaches().contains(cache)) {
//                user.getCaches().add(cache);
//            }
//        });
    }

    private void init(String timurName, String userName, String cacheName, int userCount, int cacheCount) {
        Timur timur = new Timur(timurName);
        timurRepository.save(timur);
        int counter = 0;
        for (int i = 0; i < userCount; i++) {
            User user1 = new User(userName + i, timur.getId());
            for (int j = 0; j < cacheCount; j++) {
                initCache(user1, cacheName, counter++);
            }
            userRepository.save(user1);
        }
    }

    public void initCache(User user, String cacheName, int count) {
        //user.add(new Cache(cacheName + count, null));
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