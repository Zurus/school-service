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

@UtilityClass
public class JsonUtil {


    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    private static ObjectMapper objectMapper;

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = objectMapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionTextUtil.invalidReadArrayFromJson(json), e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionTextUtil.invalidReadFromJson(json), e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            String s = objectMapper.writeValueAsString(obj);
            return s;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(ExceptionTextUtil.invalidWriteToJson(obj), e);
        }
    }


    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put(addName, addValue);
        }};
        return writeAdditionProps(obj, map);
    }

    public static <T> String writeAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = (Map<String, Object>) objectMapper.convertValue(obj, new TypeReference<T>() {
        });
        map.putAll(addProps);
        return writeValue(map);
    }
}
