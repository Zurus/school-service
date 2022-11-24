package ru.secure_environment.arm.notification.telegram.command;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.secure_environment.arm.notification.common.NotificationSubscribeEnum;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;
import ru.secure_environment.arm.services.TelegramService;

public class StartCommand extends Command {

    protected TelegramService telegramService;
    private final static String START_SUCCESS = "Вы успешно подписались на рассылку.\n";
    private final static String START_ERROR = "Вы уже подписаны на рассылку.\n";

    public StartCommand(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update, TelegramBotSender sender) {
        NotificationSubscribeEnum result = telegramService.start(getUserName(update), getChatId(update).toString());
        Long chatId = getChatId(update);
        if (result == NotificationSubscribeEnum.ALREADY_STARTED) {
            sender.sendMessage(chatId, START_ERROR);
        } else {
            sender.sendMessage(chatId, START_SUCCESS);
        }
    }
}
