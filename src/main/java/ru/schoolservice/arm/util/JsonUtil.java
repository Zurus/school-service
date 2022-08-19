package ru.schoolservice.arm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import ru.schoolservice.arm.model.User;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class JsonUtil {

    private static final String EMBEDDED = "_embedded";
    private static final String USERS = "users";

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    private static ObjectMapper objectMapper;

    public static <T> List<T> readValues(String json, Class<T> clazz) throws IOException {

        JsonNode node = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readTree(json)
                .path(EMBEDDED).path(USERS);

        ObjectReader reader = objectMapper.readerFor(clazz);
        return reader.<T>readValues(node.toPrettyString()).readAll();
    }

    public static <T> T readValue(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> String writeValue(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
