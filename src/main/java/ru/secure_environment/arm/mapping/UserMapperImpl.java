package ru.secure_environment.arm.mapping;

import org.springframework.stereotype.Component;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public User toModel(UserDto userDto) {
        return null;
    }
}
