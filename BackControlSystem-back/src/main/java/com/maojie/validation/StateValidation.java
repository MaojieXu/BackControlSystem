package com.maojie.validation;

import com.maojie.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @Description: Custom validator for the @State annotation, ensuring the value is either "Published" (Published) or "Draft" (Draft).
 */
public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * @Description: Validates if the provided string is either "Published" or "Draft".
     * @Param: value - The string value to validate; context - ConstraintValidatorContext
     * @return: boolean - True if valid, false otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Null values are considered invalid
        }
        // Check if the value matches one of the allowed states
        return value.equals("Published") || value.equals("Draft");
    }
}
