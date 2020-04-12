package com.blesk.accountservice.Handler;

import com.blesk.accountservice.DTO.Response;
import com.blesk.accountservice.Exception.AccountServiceException;
import com.blesk.accountservice.Value.Messages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class AccountServiceHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Response> handleTypeMismatchException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.TYPE_MISMATCH_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorObj = new LinkedHashMap<>();
        errorObj.put("timestamp", new Date());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        errorObj.put("validations", errors);
        errorObj.put("error", true);

        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Response> handleRequestBodyNotFoundException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.REQUEST_BODY_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountServiceException.class)
    public final ResponseEntity<Response> handleResourcesException(Exception ex) {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), ex.getMessage(), true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Response> handleNotFoundError() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.PAGE_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Response> handlePageNotFoundException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.PAGE_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Response> handelNullPointerExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.NULL_POINTER_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<Response> handleDatabaseExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.SQL_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}