package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import ru.secure_environment.arm.dto.ContactDto;
import ru.secure_environment.arm.model.Contact;

import java.util.List;

@Mapper(uses = ContactMapper.class)
public interface ContactListMapper {
    List<ContactDto> toDTO(List<Contact> contact);


    List<Contact> toModel(List<ContactDto> contactDto);
}
