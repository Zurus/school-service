package ru.secure_environment.arm.util;

import lombok.experimental.UtilityClass;
import ru.secure_environment.arm.model.User;

@UtilityClass
public class ExceptionTextUtil {

    public static String employeeValidationError(User user) {
        return "for employee '" + user + "' job title must not be null or empty";
    }

    public static String studentValidationError(User user) {
        return "for student '" + user + "' classNumber must not be null or empty";
    }

    public static String idWasNotFound(int id) {
        return "User '" + id + "' was not found";
    }

    public static String invalidReadArrayFromJson(String json) {
        return "Invalid read array from JSON:\n'" + json + "'";
    }

    public static String invalidReadFromJson(String json) {
        return "Invalid read array from JSON:\n'" + json + "'";
    }

    public static String invalidWriteToJson(Object obj) {
        return "Invalid write to Json: \n'" + obj + "'";
    }
}
