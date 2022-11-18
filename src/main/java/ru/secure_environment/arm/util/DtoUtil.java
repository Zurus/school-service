package ru.secure_environment.arm.util;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.util.validation.EventDtoRequirementComplianceEnum;

import java.util.Objects;

@UtilityClass
public class DtoUtil {

    public static EventDtoRequirementComplianceEnum getEventDtoRequirementComplianceEnum(EventDto eventDto) {
        return EventDtoRequirementComplianceEnum.getEventDtoRequirementComplianceEnum(isEventDtoInvalid(eventDto));
    }

    /**
     * Проверка корректности события
     *
     * @param eventDto событие в контроллере Сигур'а
     * @return результат проверки
     */
    public static Boolean isEventDtoInvalid(EventDto eventDto) {
        return !eventDtoHasCardLoadedInSigurDB(eventDto) || hasEmptyFields(eventDto);
    }

    public static Boolean eventDtoHasCardLoadedInSigurDB(EventDto eventDto) {
        /*
         * Параметр internalEmpId == 0 если объект отсутствует в БД СИГУР'а
         */
        return eventDto.getInternalEmpId() != 0;
    }

    public static Boolean hasEmptyFields(EventDto eventDto) {
        return Objects.isNull(eventDto.getDirection())
                || Objects.isNull(eventDto.getKeyHex())
                || Objects.isNull(eventDto.getLogId())
                || Objects.isNull(eventDto.getTime());
    }
}
