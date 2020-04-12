package com.blesk.accountservice.Config;

import com.blesk.accountservice.DTO.JwtMapper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        converter.setAccessTokenConverter(this);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        JwtMapper jwtMapper = new JwtMapper();

        if (map.get("access_token") != null)
            jwtMapper.setAccess_token((String) map.get("access_token"));

        if (map.get("token_type") != null)
            jwtMapper.setToken_type((String) map.get("token_type"));

        if (map.get("refresh_token") != null)
            jwtMapper.setRefresh_token((String) map.get("refresh_token"));

        if (map.get("expires_in") != null)
            jwtMapper.setExpires_in((Integer) map.get("expires_in"));

        if (map.get("account_id") != null)
            jwtMapper.setAccount_id((Integer) map.get("account_id"));

        if (map.get("login_id") != null)
            jwtMapper.setLogin_id((Integer) map.get("login_id"));

        if (map.get("user_name") != null)
            jwtMapper.setUser_name((String) map.get("user_name"));

        if (map.get("balance") != null)
            jwtMapper.setBalance((Double) map.get("balance"));

        if (map.get("activated") != null)
            jwtMapper.setActivated((Boolean) map.get("activated"));

        if (map.get("jti") != null)
            jwtMapper.setJti((String) map.get("jti"));

        oAuth2Authentication.setDetails(jwtMapper);
        return oAuth2Authentication;
    }
}