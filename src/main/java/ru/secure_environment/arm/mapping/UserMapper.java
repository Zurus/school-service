package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDTO(User user);

    User toModel(UserDto userDto);
}
