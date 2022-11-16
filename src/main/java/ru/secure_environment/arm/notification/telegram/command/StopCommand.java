package ru.secure_environment.arm.notification.telegram.command;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.secure_environment.arm.notification.common.NotificationSubscribeEnum;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;

@AllArgsConstructor
public class StopCommand extends Command {

    private final static String STOP_SUCCESS = "Вы успешно отписались от рассылки.\n";
    private final static String STOP_ERROR = "Вы уже отписались от рассылки.\n";

    @Override
    public void execute(Update update, TelegramBotSender sender) {
        NotificationSubscribeEnum result = telegramService.stop(getUserName(update));
        Long chatId = getChatId(update);
        if (result == NotificationSubscribeEnum.ALREADY_STOPPED) {
            sender.sendMessage(chatId, STOP_ERROR);
        } else {
            sender.sendMessage(chatId, STOP_SUCCESS);
        }
    }
}
