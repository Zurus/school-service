package ru.secure_environment.arm.notification.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.secure_environment.arm.services.TelegramService;

import java.util.HashMap;
import java.util.Map;

import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.*;


@Slf4j
@Component
public class CommandContainer {

    private TelegramService telegramService;
    private final Map<String, Command> commandMap;

    @Autowired
    public CommandContainer(TelegramService telegramService) {
        this.telegramService = telegramService;
        commandMap = new HashMap<String, Command>() {{
            put(START.getCommandName(), new StartCommand(telegramService));
            put(STOP.getCommandName(), new StopCommand(telegramService));
            put(HELP.getCommandName(), new HelpCommand());
            put(NO.getCommandName(), new NoCommand());
        }};
    }

    public Command getCommandByName(String commandName) {
        return commandMap.getOrDefault(commandName, commandMap.get(NO.getCommandName()));
    }
}
