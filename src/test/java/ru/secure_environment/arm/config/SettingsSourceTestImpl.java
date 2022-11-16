package ru.secure_environment.arm.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
@Slf4j
@Profile("test")
public class SettingsSourceTestImpl implements SettingsSource {

    private final Map<String, String> settingsService = new HashMap<String, String>() {{
       put("test.key", "test.value");
       put(BOT_NAME, BOT_NAME);
       put(BOT_TOKEN,BOT_TOKEN);
    }};

    @Override
    public String getValueByKey(String key) {
        return settingsService.get(key);
    }
}
