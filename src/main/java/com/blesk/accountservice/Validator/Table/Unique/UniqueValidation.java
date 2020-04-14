package com.blesk.accountservice.Validator.Table.Unique;

import com.blesk.accountservice.Component.ApplicationContext.ApplicationContextProviderImpl;
import com.blesk.accountservice.Validator.Service.FieldValueExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidation implements ConstraintValidator<Unique, Object> {

    private FieldValueExists fieldValueExists;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> c = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.fieldValueExists = (FieldValueExists) ApplicationContextProviderImpl.getBean(serviceQualifier, c);
        } else {
            this.fieldValueExists = (FieldValueExists) ApplicationContextProviderImpl.getBean(c);
        }
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        return !this.fieldValueExists.fieldValueExists(object, this.fieldName);
    }
}