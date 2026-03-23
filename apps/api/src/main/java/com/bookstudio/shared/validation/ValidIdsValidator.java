package com.bookstudio.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidIdsValidator implements ConstraintValidator<ValidIds, List<Long>> {

    @Override
    public void initialize(ValidIds constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Long> list, ConstraintValidatorContext context) {
        if (list == null) {
            return true;
        }
        return list.stream().allMatch(id -> id != null && id >= 1);
    }
}