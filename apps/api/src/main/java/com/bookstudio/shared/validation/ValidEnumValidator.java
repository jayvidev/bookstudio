package com.bookstudio.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {
    private ValidEnum annotation;
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        boolean valid = Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equals(value));
        if (!valid) {
            String allowedValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(annotation.message() + ". Allowed values: " + allowedValues)
                    .addConstraintViolation();
        }
        return valid;
    }
}