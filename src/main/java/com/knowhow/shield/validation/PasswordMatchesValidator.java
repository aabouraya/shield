package com.knowhow.shield.validation;

import com.knowhow.shield.dto.RegistrationDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistrationDto registration, ConstraintValidatorContext context) {
        return registration.getPassword().equals(registration.getMatchingPassword());
    }

}
