package ru.schoolservice.arm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionTextUtil {

    public static String idWasNotFound(int id) {
        return "User '" + id + "' was not found";
    }
}