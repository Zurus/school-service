package ru.secure_environment.arm;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.SchoolDto;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.Role;
import ru.secure_environment.arm.model.School;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.CardKeyUtil;
import ru.secure_environment.arm.util.JsonUtil;

import java.util.Collections;
import java.util.EnumSet;

@UtilityClass
public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NEW_ID = 5;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final String USER_PHONE = "+79026165214";
    public static final String ADMIN_PHONE = "+79872832442";
    public static final String SCHOOL_ID = "A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6";
    public static final String USER_CARD_ID_DEC = "056,35766";
    public static final String USER_CARD_ID_HEX = "388BB6";
    public static final String ADMIN_CARD_ID = "251,35066";
    public static final String USER_TELEGRAM = "asdf";
    public static final Boolean USER_IS_EMPLOYEE = false;
    public static final String CLASS_NUMBER = "1A";
    public static final Boolean CLASS_ROOM_TEACHER = false;

    public static final String NEW_NAME = "new_name";
    public static final String NEW_MAIL = "some@mail.ru";
    public static final String NEW_PASS = "pass";
    public static final String NEW_PHONE = "+79171131442";
    public static final String NEW_USER_CARD_ID = "156,35761";
    public static final String NEW_TELEGRAM = "telega";


    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", USER_PHONE, USER_CARD_ID_HEX, USER_TELEGRAM, Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", ADMIN_PHONE, ADMIN_CARD_ID, null, Role.USER, Role.ADMIN);
    public static final User newUser = new User(NEW_ID, NEW_NAME, NEW_MAIL, NEW_PASS, NEW_PHONE, CardKeyUtil.toHexString(NEW_USER_CARD_ID), NEW_TELEGRAM, Role.USER);

    public static final UserDto userDto =
            new UserDto.Builder(USER_ID, "User")
                    .email(USER_MAIL)
                    .phoneNumber(USER_PHONE)
                    .schoolId(SCHOOL_ID)
                    .cardId(USER_CARD_ID_DEC)
                    .telegram(USER_TELEGRAM)
                    .classNumber(CLASS_NUMBER)
                    .roles(Collections.singleton(Role.USER))
                    .password("asdfadsf")
                    .build();

    public static final UserDto adminDto =
            new UserDto.Builder(ADMIN_ID, "Admin")
                    .email(ADMIN_MAIL)
                    .phoneNumber(ADMIN_PHONE)
                    .schoolId(SCHOOL_ID)
                    .cardId(ADMIN_CARD_ID)
                    .classNumber(CLASS_NUMBER)
                    .roles(EnumSet.of(Role.USER, Role.ADMIN))
                    .build();

    public static final UserDto sigurMock =
            new UserDto.Builder(3, "sigur_AI")
                    .email("sigur@javaops.ru")
                    .phoneNumber("+11111111111")
                    .schoolId(SCHOOL_ID)
                    .cardId("111,12345")
                    .classNumber(CLASS_NUMBER)
                    .roles(EnumSet.of(Role.USER, Role.SIGUR))
                    .build();

    public static final UserDto newDto =
            new UserDto.Builder(null, NEW_NAME)
                    .email("some@mail.ru")
                    .phoneNumber(NEW_PHONE)
                    .schoolId(SCHOOL_ID)
                    .cardId(NEW_USER_CARD_ID)
                    .telegram(NEW_TELEGRAM)
                    .classNumber(CLASS_NUMBER)
                    .roles(Collections.singleton(Role.USER))
                    .password(NEW_PASS)
                    .build();

    public static final UserDto invalidDto =
            new UserDto.Builder(null, "")
                    .email("11111")
                    .phoneNumber("asdfasdf")
                    .schoolId("school_id")
                    .cardId(null)
                    .classNumber(CLASS_NUMBER)
                    .roles(Collections.singleton(Role.USER))
                    .password(NEW_PASS)
                    .build();

    public static final SchoolDto schoolDto = new SchoolDto(SCHOOL_ID, "Аксаковская гимназия №11");

    public static final MatcherFactory.Matcher<School> SCHOOL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(School.class);
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "schoolClass");
    public static final MatcherFactory.Matcher<UserDto> USER_MATCHER_DTO = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "password");

    public static School getNewSchool() {
        School school = new School();
        school.setName("Крутая школа 1");
        school.setId("1A2B3C4D5E6F7A8B9C0D1E2F3A4B5C6D");

        return school;
    }

    public static String eventString = "{\n" +
            "  \"logs\": [\n" +
            "    {\n" +
            "      \"logId\": \"100171\",\n" +
            "      \"time\": \"1665603127\",\n" +
            "      \"empId\": \"\",\n" +
            "      \"internalEmpId\": \"662\",\n" +
            "      \"accessPoint\": \"1\",\n" +
            "      \"direction\": \"2\",\n" +
            "      \"keyHex\": \"388BB6\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"logId\": \"100174\",\n" +
            "      \"time\": 1665656611,\n" +
            "      \"empId\": \"\",\n" +
            "      \"internalEmpId\": 662,\n" +
            "      \"accessPoint\": 1,\n" +
            "      \"direction\": 2,\n" +
            "      \"keyHex\": \"388BB6\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"logId\": 100175,\n" +
            "      \"time\": 1665656637,\n" +
            "      \"empId\": \"\",\n" +
            "      \"internalEmpId\": 662,\n" +
            "      \"accessPoint\": 1,\n" +
            "      \"direction\": 2,\n" +
            "      \"keyHex\": \"FB88FA\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"logId\": 100176,\n" +
            "      \"time\": 1665656755,\n" +
            "      \"empId\": \"\",\n" +
            "      \"internalEmpId\": 662,\n" +
            "      \"accessPoint\": 1,\n" +
            "      \"direction\": 2,\n" +
            "      \"keyHex\": \"FB88FA\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    public static User getNew() {
        User updateUser = new User();
        updateUser.updateUser(user);
        updateUser.setId(null);
        updateUser.setName("new Name");
        updateUser.setEmail("new_Emal@mal.ru");
        updateUser.setPassword("new Pass");

        return updateUser;
    }

    public static UserDto getInvalid() {
        userDto.setName(null);
        userDto.setPhoneNumber("asdfasdf");

        return userDto;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static String jsonWithPassword(UserDto user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
