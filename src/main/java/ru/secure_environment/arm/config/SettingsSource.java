package ru.secure_environment.arm.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import ru.secure_environment.arm.services.SettingsService;

@Configuration
@AllArgsConstructor
@Slf4j
public class SettingsSource {

    private static final String BOT_NAME = "bot.name";
    private static final String BOT_TOKEN = "bot.token";
    private final SettingsService settingsService;

    public String getBotName() {
        return getValueByKey(BOT_NAME);
    }

    public String getBotToken() {
        return getValueByKey(BOT_TOKEN);
    }

    public String getValueByKey(String key) {
        log.info("Поиск настроек из БД key = " + settingsService.getSettingByKey(BOT_TOKEN));
        return settingsService.getSettingByKey(key);
    }
}
