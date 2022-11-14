package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secure_environment.arm.error.ServerWorkException;
import ru.secure_environment.arm.repository.SettingsRepository;

@Service
@AllArgsConstructor
@Slf4j
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public String getSettingByKey(String key) {
        log.info("load setting {} from db", key);
        return settingsRepository.getSettingsByKey(key)
                .orElseThrow(()->new ServerWorkException("cannot find property " + key)).getValue();
    }
}
