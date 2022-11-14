package ru.secure_environment.arm.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.secure_environment.arm.config.SettingsSource;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@Profile("prod")
public class TelegramBotNotification {

    @Autowired
    public TelegramBotNotification(SettingsSource settingsSource) {
        this.settingsSource = settingsSource;
    }
    private SettingsSource settingsSource;

    private String botName;
    private String botToken;

    @PostConstruct
    private void init() {
        botName = settingsSource.getBotName();
        botToken = settingsSource.getBotToken();
    }
}
