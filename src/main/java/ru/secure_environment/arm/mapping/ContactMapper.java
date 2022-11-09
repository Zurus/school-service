package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import ru.secure_environment.arm.dto.ContactDto;
import ru.secure_environment.arm.model.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDto toDTO(Contact contact);

    Contact toModel(ContactDto contactDto);
}
