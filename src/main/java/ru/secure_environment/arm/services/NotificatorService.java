package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.notification.telegram.TelegramBotSender;
import ru.secure_environment.arm.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.secure_environment.arm.model.enums.EventEnum.getTextDirection;

@Service
@Slf4j
@AllArgsConstructor
public class NotificatorService {

    private static final String NOTIFICATION =
            "→\n" +
                    "%s в %s успешно %s.\n" +
                    "←\n";

    private final TelegramBotSender telegramBotSender;
    private final UserRepository userRepository;

    @Transactional
    public void sendNotifications(List<EventResultDto> list) {
        log.info("sending notifications {}", list);
        List<String> cards = list
                .stream()
                .map(EventResultDto::getCardId)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAllUserByCard(cards);

        users.forEach(user -> {
            user.getContacts()
                    .forEach(contact -> {
                        String chatId = contact.getChatId();
                        EventResultDto eventResultDto = getByCard(list, user.getCard().getCardId());
                        telegramBotSender.sendMessage(chatId,
                                String.format(NOTIFICATION,
                                        user.getName(),
                                        eventResultDto.getTextDate(),
                                        getTextDirection(eventResultDto.getDirection()))
                        );
                    });
        });
        //todo:добавить сохранение уведомлений в базу
    }

    private EventResultDto getByCard(List<EventResultDto> list, String cardId) {
        return list
                .stream()
                .filter(s -> s.getCardId().equals(cardId))
                .findFirst().get();
    }
}
