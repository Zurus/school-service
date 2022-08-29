package ru.secure_environment.arm.deprecated;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.JsonUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.secure_environment.arm.web.user.UserTestData.admin;
import static ru.secure_environment.arm.web.user.UserTestData.user;

@Deprecated
public class UserTestUtil {

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
