package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.UserRepository;

import java.util.List;
import java.util.Objects;
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

    private final UserRepository userRepository;

    @Transactional
    public void sendNotifications(List<EventResultDto> list) {
        log.info("sending notifications {}", list);
        List<String> cards = list
                .stream()
                .map(EventResultDto::getCardId)
                .collect(Collectors.toList());

    }

    private EventResultDto getByCard(List<EventResultDto> list, String cardId) {
        return list
                .stream()
                .filter(s -> s.getCardId().equals(cardId))
                .findFirst().get();
    }
}
