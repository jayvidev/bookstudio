package com.bookstudio.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, List<?>> {

    @Override
    public void initialize(NoNullElements constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        if (list == null) {
            return true;
        }
        return list.stream().noneMatch(item -> item == null);
    }
}