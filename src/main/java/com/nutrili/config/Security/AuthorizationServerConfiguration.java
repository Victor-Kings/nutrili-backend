package com.nutrili.config.Security;

import com.nutrili.service.NutriliUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
    @EnableAuthorizationServer
     public  class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private static final String RESOURCE_ID = "restservice";

        @Autowired
        private TokenStore tokenStore ;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private NutriliUserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Value("${user.oauth.clientId}")
        private  String clientID;

        @Value("${user.oauth.clientSecret}")
        private  String clientSecret;

        @Autowired
        JwtAccessTokenConverter jwtAccessTokenConverter;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(clientID)
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .scopes("all")
                    .refreshTokenValiditySeconds(300000)
                    .resourceIds(RESOURCE_ID)
                    .secret(passwordEncoder.encode(clientSecret))
                    .accessTokenValiditySeconds(50000);
        }

        @Bean
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }
