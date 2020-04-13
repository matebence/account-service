package com.blesk.accountservice.Validator.Service;

public interface FieldValueExists {

    Boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException;
}