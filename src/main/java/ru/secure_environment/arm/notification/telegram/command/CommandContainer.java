package ru.secure_environment.arm.notification.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.HELP;
import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.NO;
import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.START;
import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.STOP;


@Slf4j
public class CommandContainer {

    private final Map<String, Command> commandMap;

    public CommandContainer() {
        commandMap = new HashMap<String, Command>() {{
            put(START.getCommandName(), new StartCommand());
            put(STOP.getCommandName(), new StartCommand());
            put(HELP.getCommandName(), new HelpCommand());
            put(NO.getCommandName(), new NoCommand());
        }};
    }

    public Command getCommandByName(String commandName) {
        return commandMap.getOrDefault(commandName, commandMap.get(NO.getCommandName()));
    }
}
