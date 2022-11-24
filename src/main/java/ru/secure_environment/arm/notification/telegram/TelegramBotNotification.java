package ru.secure_environment.arm.notification.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.secure_environment.arm.config.SettingsSource;
import ru.secure_environment.arm.notification.telegram.command.CommandContainer;

import javax.annotation.PostConstruct;

import static ru.secure_environment.arm.notification.telegram.command.CommandEnum.NO;


@Component
@Slf4j
public class TelegramBotNotification extends TelegramLongPollingBot implements TelegramBotSender {

    private static final String COMMAND_PREFIX = "/";
    private final SettingsSource settingsSource;
    private final CommandContainer commandContainer;
    protected String botName;
    protected String botToken;

    @Autowired
    public TelegramBotNotification(SettingsSource settingsSource, CommandContainer commandContainer) {
        this.settingsSource = settingsSource;
        this.commandContainer = commandContainer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("{} sent message {}", update.getMessage().getChatId(), update.getMessage().getText());
        if (update.hasMessage()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                commandContainer.getCommandByName(message).execute(update, this);
            } else {
                commandContainer.getCommandByName(NO.getCommandName()).execute(update, this);
            }
        }
    }

    @Override
    public void sendMessage(String chatId, String message) {
        log.info("we sent message {} to {}", message, chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("telegram bot error {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    private void init() {
        botName="SecureEnvBot";
        botToken="5596926556:AAFHZqdaMFrDZ1Dm7fxw404RodHM3p01dzc";
        //botName = settingsSource.getBotName();
        //botToken = settingsSource.getBotToken();
    }
}
