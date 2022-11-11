package ru.secure_environment.arm.util.validation;

import ru.secure_environment.arm.dto.ContactDto;
import ru.secure_environment.arm.model.common.HasId;

import java.util.List;

public interface HasEmailAndContactsAndId extends HasId {
    String getEmail();

    List<ContactDto> getContacts();
}
