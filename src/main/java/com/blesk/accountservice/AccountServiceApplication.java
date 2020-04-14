package com.blesk.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
public class AccountServiceApplication {

    public static final Long SYSTEM = 1L;

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}