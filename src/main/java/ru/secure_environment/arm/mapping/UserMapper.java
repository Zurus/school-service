package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.CardKeyUtil;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "schoolClass.name", target = "classNumber"),
            @Mapping(source = "schoolClass.school.id", target = "schoolId"),
            @Mapping(source = "password", target = "password", ignore = true),
            @Mapping(source = "cardId", target = "cardId", qualifiedByName = "cardKeyToDec")

    })
    UserDto toDTO(User user);

    @Mapping(source = "cardId", target = "cardId", qualifiedByName = "cardKeyToHex")
    User toModel(UserDto userDto);

    @Named("cardKeyToDec")
    default String defaultCardKeyToDec(String cardId) {
        return CardKeyUtil.toDecString(cardId);
    }

    @Named("cardKeyToHex")
    default String defaultCardKeyToHex(String cardId) {
        return CardKeyUtil.toHexString(cardId);
    }
}
