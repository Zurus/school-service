package ru.secure_environment.arm;

import ru.secure_environment.arm.model.Role;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.JsonUtil;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final User user = new User(USER_ID, USER_MAIL, "User", "password", EnumSet.of(Role.USER));
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin", "admin", EnumSet.of(Role.USER, Role.ADMIN));

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");

    public static User getNew() {
        return new User(null, "new@gmail.com", "New_First", "newpass", EnumSet.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "user_update@gmail.com", "User_First_Update", "password_update", EnumSet.of(Role.USER));
    }

    public static List<User> users = Arrays.asList(user, admin);

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
