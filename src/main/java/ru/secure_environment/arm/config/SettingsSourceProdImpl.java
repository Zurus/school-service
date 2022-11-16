package ru.secure_environment.arm.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.secure_environment.arm.services.SettingsService;

@Configuration
@AllArgsConstructor
@Slf4j
@Profile("prod")
public class SettingsSourceProdImpl implements SettingsSource {

    private final SettingsService settingsService;

    @Override
    public String getValueByKey(String key) {
        log.info("Поиск настроек из БД key = " + settingsService.getSettingByKey(BOT_TOKEN));
        return settingsService.getSettingByKey(key);
    }
}
