package ru.secure_environment.arm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.secure_environment.arm.util.ExceptionTextUtil.invalidReadJson;
import static ru.secure_environment.arm.util.ExceptionTextUtil.invalidWriteJson;

@UtilityClass
public class JsonUtil {

//todo: OldRealization remove
//    private static final String EMBEDDED = "_embedded";
//    private static final String USERS = "users";

    private static ObjectMapper objectMapper;
    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }


    public static <T> List<T> readValues(String json, Class<T> clazz) throws IOException {
//todo: OldRealization remove
//        JsonNode node = objectMapper
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .readTree(json)
//                .path(EMBEDDED).path(USERS);
//
//        ObjectReader reader = objectMapper.readerFor(clazz);
//        return reader.<T>readValues(node.toPrettyString()).readAll();
        ObjectReader reader = objectMapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException(invalidReadJson(json));
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(invalidReadJson(json), e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(invalidWriteJson(obj));
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put(addName, addValue);
        }};
        return writeAdditionProps(obj, map);
    }

    public static <T> String writeAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = (Map<String, Object>) objectMapper.convertValue(obj, new TypeReference<T>() {});
        map.putAll(addProps);
        return writeValue(map);
    }
}
