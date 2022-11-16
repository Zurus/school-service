package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.model.enums.EventEnum;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;
import ru.secure_environment.arm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NotificatorService {

    private static final String NOTIFICATION = "В %s %ы успешно %s.";
    private final TelegramBotSender telegramBotSender;
    private final UserRepository userRepository;

    public void sendNotifications(List<EventResultDto> list) {
        list.forEach(eventResultDto -> {
            User user = userRepository.findByCardId(eventResultDto.getCardId()).orElse(null);
            if (user.isWithNotifications()) {
                user.getContacts()
                        .stream()
                        .forEach(contact -> {
                            String chatId = contact.getChatId();
                            telegramBotSender.sendMessage(chatId,
                                    String.format(NOTIFICATION, LocalDateTime.now(), user.getName(), getTextDirection(eventResultDto.getDirection()))
                            );
                        });
            }
        });
    }

    private String getTextDirection(EventEnum eventEnum) {
        switch (eventEnum) {
            case EXIT:
                return "вышел";
            case ENTER:
                return "вошел";
            default:
                return "неизвестно";
        }
    }
}
