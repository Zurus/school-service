package ru.secure_environment.arm.util.validation;

import org.springframework.util.CollectionUtils;
import ru.secure_environment.arm.model.Contact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NoContactsValidator implements ConstraintValidator<NoContacts, List<Contact>> {

    @Override
    public boolean isValid(List<Contact> value, ConstraintValidatorContext ctx) {
        return !CollectionUtils.isEmpty(value);
    }
}
