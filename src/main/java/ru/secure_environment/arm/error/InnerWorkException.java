package ru.secure_environment.arm.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

public class InnerWorkException extends AppException {
    public InnerWorkException(String msg) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, msg, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
    }
}
