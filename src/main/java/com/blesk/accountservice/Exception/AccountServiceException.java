package com.blesk.accountservice.Exception;

import org.springframework.http.HttpStatus;

public class AccountServiceException extends RuntimeException {

    private HttpStatus httpStatus;

    public AccountServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}