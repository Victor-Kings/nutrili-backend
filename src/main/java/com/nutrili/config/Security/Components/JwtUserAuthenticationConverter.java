package com.nutrili.config.Security.Components;

import com.nutrili.external.database.entity.User;
import com.nutrili.service.NutriliUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;
@Configuration
public class JwtUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Autowired
    NutriliUserDetailsService userDetailsService;

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            Map<String, Object> response = (Map<String, Object>) super.convertUserAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            response.put("newUser", userDetailsService.isNewUser(user));
            return response;
        }

}
