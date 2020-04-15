package com.blesk.accountservice.Config;

import com.blesk.accountservice.Handler.OAuthHandler;
import com.blesk.accountservice.EntryPoint.OAuthEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class OAuth2Resource extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new OAuthEntryPoint()).accessDeniedHandler(new OAuthHandler());
    }
}