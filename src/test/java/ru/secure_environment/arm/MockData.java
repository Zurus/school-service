package ru.secure_environment.arm;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.dto.SchoolDto;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.Card;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.model.enums.Role;
import ru.secure_environment.arm.util.CardKeyUtil;

import java.util.Collections;
import java.util.EnumSet;

@UtilityClass
public class MockData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NEW_ID = 8;
    public static final int EMPLOYEE_ID = 4;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final String USER_PHONE = "+79026165214";
    public static final String ADMIN_PHONE = "+79872832442";
    public static final String SCHOOL_ID = "A1B2C3D4E5F6A7B8C9D0E1F2A3B4C5D6";
    public static final String USER_CARD_ID_DEC = "056,35766";
    public static final String USER_CARD_ID_HEX = "388BB6";
    public static final String ADMIN_CARD_ID_DEC = "251,35066";
    public static final String ADMIN_CARD_ID_HEX = "FB88FA";
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

    public static final Card cardUser = new Card(1, USER_CARD_ID_HEX);
    public static final Card cardAdmin = new Card(2, ADMIN_CARD_ID_HEX);
    public static final Card newUserCard = new Card(5, CardKeyUtil.toHexString(NEW_USER_CARD_ID));

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", USER_PHONE, cardUser, USER_TELEGRAM, Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", ADMIN_PHONE, cardAdmin, null, Role.USER, Role.ADMIN);
    public static final User newUser = new User(NEW_ID, NEW_NAME, NEW_MAIL, NEW_PASS, NEW_PHONE, newUserCard, NEW_TELEGRAM, Role.USER);

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
                    .cardId(ADMIN_CARD_ID_DEC)
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

    public static final UserDto employeeDto =
            new UserDto.Builder(4, "math_teacher")
                    .email("super_teacher@mail.ru")
                    .phoneNumber("+79174816110")
                    .schoolId("A1B2C3D4E5F6A7B8C9D0E1F2A3B41234")
                    .cardId("156,58651")
                    .position("Педработники")
                    .roles(Collections.singleton(Role.USER))
                    .build();

    public static final SchoolDto schoolDto = new SchoolDto(SCHOOL_ID, "Аксаковская гимназия №11");

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

    public static String eventStringAlreadyExisted = "{\n" +
            "  \"logs\": [\n" +
            "    {\n" +
            "      \"logId\": \"100170\",\n" +
            "      \"time\": \"1665603127\",\n" +
            "      \"empId\": \"\",\n" +
            "      \"internalEmpId\": \"0\",\n" +
            "      \"accessPoint\": \"1\",\n" +
            "      \"direction\": \"2\",\n" +
            "      \"keyHex\": \"388BB6\"\n" +
            "    }] }";


    //public static Event event = new Event(cardUser, );

    public static final EventResultDto RESULT_DTO = new EventResultDto(100176);
}
