package ru.schoolservice.arm;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.schoolservice.arm.model.Role;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.util.JsonUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTestUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final User user = new User(USER_ID, USER_MAIL, "User_First", "User_Last", "password", EnumSet.of(Role.USER));
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin_First", "Admin_Last", "admin", EnumSet.of(Role.USER, Role.ADMIN));

    public static User getNew() {
        return new User(null, "new@gmail.com", "New_First", "New_Last", "newpass", EnumSet.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "user_update@gmail.com", "User_First_Update", "User_Last_Update", "password_update", EnumSet.of(Role.USER));
    }

    public static List<User> users = Arrays.asList(user, admin);

    public static void assertEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(expected);
    }

    public static void assertNoIdEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "password").isEqualTo(expected);
    }

    public static User asUser(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, User.class);
    }

    public static ResultMatcher jsonMatcher(User expected, BiConsumer<User, User> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asUser(mvcResult), expected);
    }

    public static void assertUsers(MvcResult json) throws IOException {
        List<User> users = JsonUtil.readValues(json.getResponse().getContentAsString(), User.class);
        assertLists(UserTestUtil.users, users, UserTestUtil::assertNoIdEquals, User.class);
    }

    private static <T> void assertLists(List<T> expected, List<T> actual, BiConsumer<T, T> equalsAssertion, Class<T> clazz) {
        assertTrue(expected.size() == actual.size());
        Iterator<T> expectedIterator = expected.iterator();
        Iterator<T> actualIterator = actual.iterator();

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            T expectedElement = expectedIterator.next();
            T actualElement = actualIterator.next();
            equalsAssertion.accept(expectedElement, actualElement);
        }
    }

}
