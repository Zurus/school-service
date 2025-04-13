package ru.schoolservice.arm.mapper;

import ru.schoolservice.arm.dto.CacheDto;
import ru.schoolservice.arm.dto.TimurDto;
import ru.schoolservice.arm.dto.UserDto;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.Timur;
import ru.schoolservice.arm.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ToEntity {
    public static Cache toEntity(CacheDto dto) {
        Cache entity = new Cache();
        entity.setId(dto.getId());
        entity.setCache(dto.getCache());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            //entity.setUser(user);
        }

        return entity;
    }

    public static Timur toEntity(TimurDto dto) {
        Timur entity = new Timur();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setTimurId(dto.getTimurId());

        // Маппинг списка Cache
        if (dto.getCaches() != null) {
            List<Cache> caches = dto.getCaches().stream()
                    .map(cacheDto -> {
                        Cache cache = toEntity(cacheDto);
                        //cache.setUser(entity);  // Устанавливаем обратную связь
                        return cache;
                    })
                    .collect(Collectors.toList());
            //entity.setCaches(caches);
        }

        return entity;
    }
}
