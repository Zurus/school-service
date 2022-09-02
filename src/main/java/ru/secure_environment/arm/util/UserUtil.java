package ru.secure_environment.arm.util;


import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;

import java.util.Objects;

import static ru.secure_environment.arm.config.WebSecurityConfig.PASSWORD_ENCODER;


@UtilityClass
public class UserUtil {
    public static User updateFromDto(UserDto dto, User user) {
        user.updateFromDto(dto);
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
