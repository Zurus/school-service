package ru.secure_environment.arm.mapping;

import org.springframework.stereotype.Component;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListMapperImpl implements UserListMapper {

    private final UserMapper userMapper;

    public UserListMapperImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<User> toUserList(List<UserDto> userDtos) {
        return null;
    }
}
