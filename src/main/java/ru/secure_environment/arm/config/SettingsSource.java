package ru.secure_environment.arm.config;

public interface SettingsSource {
     static final String BOT_NAME = "bot.name";
     static final String BOT_TOKEN = "bot.token";

    default String getBotName() {
        return getValueByKey(BOT_NAME);
    }

    default String getBotToken() {
        return getValueByKey(BOT_TOKEN);
    }

    String getValueByKey(String key);
}
