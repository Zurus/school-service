package ru.secure_environment.arm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionTextUtil {

    public static String userWasNotFound(int id) {
        return "User '" + id + "' was not found";
    }

    public static String userWasNotFound(String email) {
        return "User '" + email + "' was not found";
    }

    public static String mustBeNew(Object bean) {
        return bean.getClass().getSimpleName() + " must be new (id=null)";
    }

    public static String mustHasId(Object bean, int id) {
        return bean.getClass().getSimpleName() + " must has id=" + id;
    }

    public static String invalidReadJson(String json) {
        return "invalid read from JSON:\n'" + json + "'";
    }

    public static <T> String invalidWriteJson(T obj) {
        return "Invalid write to JSON:\n'" + obj + "'";
    }
}
