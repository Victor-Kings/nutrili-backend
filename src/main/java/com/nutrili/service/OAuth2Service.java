package com.nutrili.service;

import com.nutrili.exception.SomethingWentWrongException;
import com.nutrili.external.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OAuth2Service {

    @Autowired
    DefaultTokenServices defaultTokenServices;

    @Autowired
    TokenStore tokenStore;
    public void revokeToken(User user){
        try {
            Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(
                    "teste", user.getUsername());
            for (OAuth2AccessToken token : tokens) {
                defaultTokenServices.revokeToken(token.getValue().toString());
            }
        } catch(Exception e ){
            throw new SomethingWentWrongException();
        }
    }
}
