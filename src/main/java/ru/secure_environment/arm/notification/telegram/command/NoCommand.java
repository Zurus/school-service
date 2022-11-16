package ru.secure_environment.arm.notification.telegram.command;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;

@AllArgsConstructor
public class NoCommand extends Command {

    private final static String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
            + "Чтобы посмотреть список коммнд введите /help";

    @Override
    public void execute(Update update, TelegramBotSender telegramBotSender) {
        telegramBotSender.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
