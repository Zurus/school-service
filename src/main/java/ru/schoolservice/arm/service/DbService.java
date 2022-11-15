package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.schoolservice.arm.repository.SettingsRepository;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private SettingsRepository settingsRepository;
    private static final String KEY = "KEY";

    public String getKey () {
        return settingsRepository
                .findByKey(KEY)
                .orElseThrow(()-> new RuntimeException("не найден параметр: " + KEY )).getValue();
    }
}
