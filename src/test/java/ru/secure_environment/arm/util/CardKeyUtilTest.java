package ru.secure_environment.arm.util;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.junit.jupiter.api.Test;
import ru.secure_environment.arm.MatcherFactory;

import java.time.LocalDateTime;

class CardKeyUtilTest {

    public static final MatcherFactory.Matcher<String> USER_MATCHER = MatcherFactory.usingEqualsComparator(String.class);

    @Test
    public void testConvertToDec() {
        String hex = "388BB6";
        String expectedDec = "056,35766";
        USER_MATCHER.assertMatch(expectedDec, CardKeyUtil.toDecString(hex));
//        System.out.println(LocalDateTime.now());
//        String str = "2022-10-05T15:46:01.983";
//        LocalDateTime ldt = LocalDateTime.parse("2022-10-05T15:46:01.983");
//        System.out.println(ldt);
    }
}