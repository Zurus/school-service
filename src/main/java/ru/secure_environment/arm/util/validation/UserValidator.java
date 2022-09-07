package ru.secure_environment.arm.util.validation;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.util.ExceptionTextUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class UserValidator implements Validator {

    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (request.getMethod().equals("PUT") || request.getMethod().equals("POST")) {
            User user = (User) target;
            log.info("User '" + user + "' validation");

            if (user.getIsEmployee() && (StringUtils.isEmpty(user.getJobTitle()) || Objects.nonNull(user.getJobTitle()))) {
                errors.rejectValue("jobTitle", "", ExceptionTextUtil.employeeValidationError(user));
            }

            if (user.getIsEmployee() && (StringUtils.isEmpty(user.getClassNumber()) || Objects.nonNull(user.getClassNumber()))) {
                errors.rejectValue("classNumber", "", ExceptionTextUtil.studentValidationError(user));
            }
        }
    }
}
