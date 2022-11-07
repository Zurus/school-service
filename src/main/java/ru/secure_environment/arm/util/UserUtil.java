package ru.secure_environment.arm.util;


import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.model.User;

import static ru.secure_environment.arm.config.WebSecurityConfig.PASSWORD_ENCODER;


@UtilityClass
public class UserUtil {

    public static final String EMPL_TECHNICAL = "Техперсонал";
    public static final String EMPL_TEACH = "Педработники";

    /**
     * Является ли пользователь сотрудником
     * true - сотрудник
     * false - ученик
     */
    public static Boolean isEmployee(String schoolClass) {
        return schoolClass.equalsIgnoreCase(EMPL_TECHNICAL) || schoolClass.equalsIgnoreCase(EMPL_TEACH);
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
