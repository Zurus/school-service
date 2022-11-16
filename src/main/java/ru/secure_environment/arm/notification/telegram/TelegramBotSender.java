package ru.secure_environment.arm.notification.telegram;

public interface TelegramBotSender {

    default void sendMessage(Long chatId, String message) {
        this.sendMessage(chatId.toString(), message);
    }

    void sendMessage(String chatId, String message);
}
