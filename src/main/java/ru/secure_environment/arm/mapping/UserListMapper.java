package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> toUserListDto(List<User> users);
    List<User> toUserList(List<UserDto> userDtos);
}
