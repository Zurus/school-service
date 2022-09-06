package ru.secure_environment.arm;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.Role;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.JsonUtil;

import java.util.Collections;
import java.util.EnumSet;

@UtilityClass
public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final String USER_PHONE = "+79026165214";
    public static final String ADMIN_PHONE = "+79872832442";
    public static final String USER_SCHOOL_ID = "34543-101";
    public static final String ADMIN_SCHOOL_ID = "34543-101";
    public static final String USER_CARD_ID = "524362-80";
    public static final String ADMIN_CARD_ID = "317252-60";
    public static final String USER_TELEGRAM = "asdf";
    public static final Boolean USER_IS_EMPLOYEE = false;
    public static final Boolean ADMIN_IS_EMPLOYEE = true;
    public static final String CLASS_NUMBER = "1A";
    public static final String JOB_TITLE = "Трудовик";
    public static final Boolean CLASS_ROOM_TEACHER = false;

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", USER_PHONE, USER_SCHOOL_ID, USER_CARD_ID, USER_TELEGRAM, USER_IS_EMPLOYEE, CLASS_NUMBER, null, null, Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", ADMIN_PHONE, ADMIN_SCHOOL_ID, ADMIN_CARD_ID, null, ADMIN_IS_EMPLOYEE, null, JOB_TITLE, CLASS_ROOM_TEACHER, Role.USER, Role.ADMIN);
    public static final UserDto userDto =
            new UserDto.Builder(USER_ID, "User")
                    .email(USER_MAIL)
                    .phoneNumber(USER_PHONE)
                    .schoolId(USER_SCHOOL_ID)
                    .cardId(USER_CARD_ID)
                    .telegram(USER_TELEGRAM)
                    .isEmployee(USER_IS_EMPLOYEE)
                    .classNumber(CLASS_NUMBER)
                    .roles(Collections.singleton(Role.USER))
                    .build();

    public static final UserDto adminDto =
            new UserDto.Builder(ADMIN_ID, "Admin")
                    .email(ADMIN_MAIL)
                    .phoneNumber(ADMIN_PHONE)
                    .schoolId(ADMIN_SCHOOL_ID)
                    .cardId(ADMIN_CARD_ID)
                    .isEmployee(ADMIN_IS_EMPLOYEE)
                    .roles(EnumSet.of(Role.USER, Role.ADMIN))
                    .jobTitle(JOB_TITLE)
                    .classRoomTeacher(CLASS_ROOM_TEACHER)
                    .build();

    public static final UserDto sigurMock =
            new UserDto.Builder(3, "sigur_AI")
                    .email("sigur@javaops.ru")
                    .phoneNumber("+11111111111")
                    .schoolId("00000-000")
                    .cardId("000000-00")
                    .isEmployee(true)
                    .classNumber("")
                    .jobTitle("")
                    .roles(EnumSet.of(Role.USER, Role.SIGUR))
                    .build();


    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");
    public static final MatcherFactory.Matcher<UserDto> USER_MATCHER_DTO = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "password");

    public static User getNew() {
        User updateUser = new User();
        updateUser.updateUser(user);
        updateUser.setId(null);
        updateUser.setName("new Name");
        updateUser.setEmail("new_Emal@mal.ru");
        updateUser.setPassword("new Pass");

        return updateUser;
    }

    public static User getInvalid() {
        User invalidUser = new User();
        invalidUser.updateUser(user);
        invalidUser.setName(null);
        invalidUser.setPhoneNumber("asdfasdfas");

        return invalidUser;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
