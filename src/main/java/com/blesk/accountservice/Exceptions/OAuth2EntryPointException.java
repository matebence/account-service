package com.blesk.accountservice.Exceptions;

import com.blesk.accountservice.DTO.ErrorMessage;
import com.blesk.accountservice.Values.Messages;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class OAuth2EntryPointException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {
        ErrorMessage errorObj = new ErrorMessage(new Timestamp(System.currentTimeMillis()).toString(), Messages.AUTH_EXCEPTION);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorObj);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}