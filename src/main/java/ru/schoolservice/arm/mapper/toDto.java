package ru.schoolservice.arm.mapper;

import ru.schoolservice.arm.dto.CacheDto;
import ru.schoolservice.arm.dto.TimurDto;
import ru.schoolservice.arm.dto.UserDto;
import ru.schoolservice.arm.model.Cache;
import ru.schoolservice.arm.model.Timur;
import ru.schoolservice.arm.model.User;

import java.util.stream.Collectors;

public class toDto {
    public static CacheDto toDto(Cache entity) {
        CacheDto dto = new CacheDto();
        dto.setId(entity.getId());
        dto.setCache(entity.getCache());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        return dto;
    }

    public static TimurDto toDto(Timur entity) {
        TimurDto dto = new TimurDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setTimurId(entity.getTimurId());

//         Маппинг списка Cache
        if (entity.getCaches() != null) {
            dto.setCaches(
                    entity.getCaches().stream()
                            .map(toDto::toDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
