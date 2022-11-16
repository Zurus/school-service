package ru.secure_environment.arm.notification.telegram.command;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;

import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.HELP;
import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.START;
import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.STOP;

@AllArgsConstructor
public class HelpCommand extends Command {

    public static final String HELP_MESSAGE = String.format("●Дотупные команды● \n\n"

                    + "Начать\\закончить работу с ботом:\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"

                    + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName());

    @Override
    public void execute(Update update, TelegramBotSender telegramBotSender) {
        telegramBotSender.sendMessage(getChatId(update), HELP_MESSAGE);
    }
}
