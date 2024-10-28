package com.maojie.anno;

import com.maojie.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description: Custom annotation to validate that the state parameter can only be "published" or "draft"
 * @Param: None
 * @return: None
 * @Author: Maojie Xu
 * @Date: 10/25/2024
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {

    /**
     * @Description: Error message to be returned when the state parameter does not match allowed values
     * @Param: None
     * @return: Default message as a String
     */
    String message() default "state parameter can only be published or draft";

    /**
     * @Description: Defines the groups for which this constraint is applicable
     * @Param: None
     * @return: Array of class types for groups
     */
    Class<?>[] groups() default {};

    /**
     * @Description: Payload that can be attached to give additional information on validation failure
     * @Param: None
     * @return: Array of payload types extending Payload class
     */
    Class<? extends Payload>[] payload() default {};
}
