package com.blesk.accountservice.Validator.Table.Unique;

import com.blesk.accountservice.Validator.Service.FieldValueExists;
import com.blesk.accountservice.Value.Messages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidation.class)
@Documented
public @interface Unique {
    String message() default Messages.UNIQUE_FIELD_DEFAULT;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends FieldValueExists> service();
    String serviceQualifier() default "";
    String fieldName();
}