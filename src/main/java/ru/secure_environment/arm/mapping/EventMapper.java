package ru.secure_environment.arm.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.model.Event;
import ru.secure_environment.arm.model.enums.EventEnum;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mappings({
            @Mapping(target = "eventTime", source = "time", qualifiedByName = "unixTimeToDate"),
            @Mapping(target = "card", ignore = true),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "enumMapper")
    })
    Event toModel(EventDto userDto);

    @Named("unixTimeToDate")
    //https://translated.turbopages.org/proxy_u/en-ru.ru.e90c652f-6348f669-1c2bafe8-74722d776562/https/stackoverflow.com/questions/732034/getting-unixtime-in-java)
    default Date convertFromUnixTime(String time) {
        long unixTime = Long.valueOf(time) * 1000;
        return new Date(unixTime);
    }

    @Named("enumMapper")
    default EventEnum convertToEnum(int direction) {
        return EventEnum.parse(direction);
    }
}
