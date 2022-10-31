package ru.secure_environment.arm;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.School;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.JsonUtil;

import static ru.secure_environment.arm.MockData.userDto;
import static ru.secure_environment.arm.MockData.user;

@UtilityClass
public class UserTestUtil {


    public static final MatcherFactory.Matcher<School> SCHOOL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(School.class);
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "schoolClass");
    public static final MatcherFactory.Matcher<UserDto> USER_MATCHER_DTO = MatcherFactory.usingIgnoringFieldsComparator(UserDto.class, "password");
    public static final MatcherFactory.Matcher<EventResultDto> EVENT_RESULT_DTO_MATCHER = MatcherFactory.usingEqualsComparator(EventResultDto.class);

    public static School getNewSchool() {
        School school = new School();
        school.setName("Крутая школа 1");
        school.setId("1A2B3C4D5E6F7A8B9C0D1E2F3A4B5C6D");
        return school;
    }


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
