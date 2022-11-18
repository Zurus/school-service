package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.model.Contact;
import ru.secure_environment.arm.notification.common.NotificationSubscribeEnum;
import ru.secure_environment.arm.repository.ContactRepository;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class TelegramService {

    private final ContactRepository contactRepository;

    @Transactional
    public NotificationSubscribeEnum start(String userName, String chatId) {
        log.info("{} wants to subscribe", userName);
        Contact contact = getContact(userName);
        if (Objects.nonNull(contact.getChatId())) {
            log.warn("{} already subscribed", userName);
            return NotificationSubscribeEnum.ALREADY_STARTED;
        }
        contact.setChatId(chatId);
        contact.getUsers().forEach(user -> user.setWithNotifications(true));
        contactRepository.save(contact);
        return NotificationSubscribeEnum.SUCCESS;
    }

    @Transactional
    public NotificationSubscribeEnum stop(String userName) {
        log.info("{} wants to unsubscribe", userName);
        Contact contact = getContact(userName);
        if (Objects.isNull(contact.getChatId())) {
            log.info("{} already unsubscribe", userName);
            return NotificationSubscribeEnum.ALREADY_STOPPED;
        }
        contact.setChatId(null);
        contact.getUsers().forEach(user -> user.setWithNotifications(false));
        contactRepository.save(contact);
        return NotificationSubscribeEnum.SUCCESS;
    }

    private Contact getContact(String userName) {
        return contactRepository
                .findByTelegram(userName)
                .orElseThrow(() -> new IllegalRequestDataException("Cannot find contact with telegram = " + userName));
    }
}
