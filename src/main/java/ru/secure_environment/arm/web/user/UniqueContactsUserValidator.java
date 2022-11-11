package ru.secure_environment.arm.web.user;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.secure_environment.arm.repository.ContactRepository;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.validation.HasEmailAndContactsAndId;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueContactsUserValidator implements Validator {
    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_PHONE_NUMBER = "This phone number %s already exists";
    public static final String EXCEPTION_DUPLICATE_TELEGRAM = "This telegram %s already exists";

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasEmailAndContactsAndId.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        String requestURI = request.getRequestURI();
        if (
                !((request.getMethod().equals("PUT") || request.getMethod().equalsIgnoreCase("POST"))
                        && requestURI.contains("/account"))
        ) {
            return;
        }

        HasEmailAndContactsAndId user = ((HasEmailAndContactsAndId) target);
        //Проверяем mail на уникальность
        if (StringUtils.hasText(user.getEmail())) {
            userRepository.findByEmailIgnoreCase(user.getEmail())
                    .ifPresent(dbUser -> {
                        if (skip(dbUser.id(), user.getId())) {
                            return;
                        }
                        errors.rejectValue("email", "", EXCEPTION_DUPLICATE_EMAIL);
                    });
        }

        user.getContacts().forEach(contactDto -> {
            //Проверяем телефон на уникальность
            if (StringUtils.hasText(contactDto.getPhoneNumber())) {
                contactRepository.findByPhoneNumber(contactDto.getPhoneNumber())
                        .ifPresent(contact -> {
                            if (skip(contact.id(), contactDto.getId())) {
                                return;
                            }
                            errors.rejectValue("phoneNumber", "", String.format(EXCEPTION_DUPLICATE_PHONE_NUMBER, contactDto.getPhoneNumber()));
                        });
            }
            //Проверяем телеграм на уникальность
            if (StringUtils.hasText(contactDto.getTelegram())) {
                contactRepository.findByTelegram(contactDto.getTelegram())
                        .ifPresent(contact -> {
                            if (skip(contact.id(), contactDto.getId())) {
                                return;
                            }
                            errors.rejectValue("telegram", "", String.format(EXCEPTION_DUPLICATE_TELEGRAM, contactDto.getTelegram()));
                        });
            }
        });
    }

    private boolean skip(int dbId, Integer dtoId) {
        return dtoId != null && dbId == dtoId.intValue();
    }
}
