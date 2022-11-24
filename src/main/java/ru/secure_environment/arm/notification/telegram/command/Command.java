package ru.secure_environment.arm.notification.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;
import ru.secure_environment.arm.services.TelegramService;

@Slf4j
public abstract class Command {

    public abstract void execute(Update update, TelegramBotSender telegramBotSender);

    protected String getUserName(Update update) {
        return update.getMessage().getFrom().getUserName();
    }

    protected Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}
