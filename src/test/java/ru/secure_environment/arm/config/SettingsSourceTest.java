package ru.secure_environment.arm.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class SettingsSourceTest {

    private final static String TEST_KEY = "test.key";
    private final static String TEST_VALUE = "test.value";

    @Autowired
    private SettingsSource settingsSource;

    @Test
    public void test_valueInit() {
        String actualValue = settingsSource.getValueByKey(TEST_KEY);
        assertThat(actualValue).isEqualTo(TEST_VALUE);
    }
}