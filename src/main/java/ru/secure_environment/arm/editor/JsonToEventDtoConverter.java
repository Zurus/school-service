package ru.secure_environment.arm.editor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.error.IllegalRequestDataException;
import ru.secure_environment.arm.util.JsonUtil;

import java.io.IOException;
import java.util.List;

//https://stackoverflow.com/questions/21577782/json-parameter-in-spring-mvc-controller
@Component
public class JsonToEventDtoConverter implements Converter<String, List<EventDto>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String LOGS = "logs";

    @Override
    public List<EventDto> convert(String source) {
        List<EventDto> list = null;
        try {
            JsonNode node = objectMapper
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readTree(source)
                    .path(LOGS);

            list = JsonUtil.readValues(node.toPrettyString(), EventDto.class);
        } catch (IOException e) {
            throw new IllegalRequestDataException("parse event exception " + e);
        }
        return list;
    }
}
