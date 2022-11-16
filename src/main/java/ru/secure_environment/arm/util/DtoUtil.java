package ru.secure_environment.arm.util;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.EventDto;

import java.util.Objects;

@UtilityClass
public class DtoUtil {

    public static boolean hasEmptyFields(EventDto eventDto) {
        return Objects.nonNull(eventDto.getDirection())
                && Objects.nonNull(eventDto.getKeyHex())
                && Objects.nonNull(eventDto.getLogId())
                && Objects.nonNull(eventDto.getTime());
    }
}
