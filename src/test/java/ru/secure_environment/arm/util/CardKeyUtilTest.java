package ru.secure_environment.arm.util;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.secure_environment.arm.MatcherFactory;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CardKeyUtilTest {

    public static final MatcherFactory.Matcher<String> USER_MATCHER = MatcherFactory.usingEqualsComparator(String.class);

    @Test
    public void testConvertToDec() {
        String hex = "388BB6";
        String expectedDec = "056,35766";
        assertThat(expectedDec).isEqualTo(CardKeyUtil.toDecString(hex));
    }

    @Test
    public void testConvertToHex() {
        String decString = "056,35766";
        String expectedDec = "388BB6";
        assertThat(expectedDec).isEqualTo(CardKeyUtil.toHexString(decString));
    }
}