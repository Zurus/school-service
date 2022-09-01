package ru.secure_environment.arm.mapping;

import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface UserListMapper {

    List<UserDto> toUserDtoList(List<User> users);

    List<User> toUserList(List<UserDto> userDtos);
}
