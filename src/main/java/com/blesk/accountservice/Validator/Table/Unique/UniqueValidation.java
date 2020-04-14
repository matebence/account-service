package com.blesk.accountservice.Validator.Table;

import com.blesk.accountservice.Component.ApplicationContext.ApplicationContextProviderImpl;
import com.blesk.accountservice.Validator.Service.FieldValueExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private FieldValueExists fieldValueExists;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.fieldValueExists = (FieldValueExists) ApplicationContextProviderImpl.getBean(serviceQualifier, clazz);
        } else {
            this.fieldValueExists = (FieldValueExists) ApplicationContextProviderImpl.getBean(clazz);
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return !this.fieldValueExists.fieldValueExists(o, this.fieldName);
    }
}