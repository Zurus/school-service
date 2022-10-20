package ru.secure_environment.arm.util.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.secure_environment.arm.repository.SchoolRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@AllArgsConstructor
@Slf4j
public class HasSchoolValidator implements ConstraintValidator<HasSchool, String> {

    private final SchoolRepository repository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return repository.findById(s).isPresent();
    }
}
