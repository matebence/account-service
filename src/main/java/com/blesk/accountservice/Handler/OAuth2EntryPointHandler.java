package com.blesk.accountservice.Handler;

import com.blesk.accountservice.DTO.ErrorMessage;
import com.blesk.accountservice.Values.Messages;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class OAuth2EntryPointHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorMessage errorObj = new ErrorMessage(new Timestamp(System.currentTimeMillis()).toString(), Messages.AUTH_REQUIRED_EXCEPTION);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorObj);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}