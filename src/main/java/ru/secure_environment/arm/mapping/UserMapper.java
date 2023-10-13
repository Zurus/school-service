package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.secure_environment.arm.dto.UserDto;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.CardKeyUtil;
import ru.secure_environment.arm.util.UserUtil;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "password", target = "password", ignore = true),
    })
    UserDto toDTO(User user);

    User toModel(UserDto userDto);

    @Named("positionMapper")
    default String positionMapper(String classNumber) {
        if (UserUtil.isEmployee(classNumber)) {
            return classNumber;
        }
        return null;
    }

    @Named("classNumberMapper")
    default String classNumberMapper(String classNumber) {
        if (!UserUtil.isEmployee(classNumber)) {
            return classNumber;
        }
        return null;
    }

    @Named("cardKeyToDec")
    default String defaultCardKeyToDec(String cardId) {
        return CardKeyUtil.toDecString(cardId);
    }

    @Named("cardKeyToHex")
    default String defaultCardKeyToHex(String cardId) {
        return CardKeyUtil.toHexString(cardId);
    }
}
