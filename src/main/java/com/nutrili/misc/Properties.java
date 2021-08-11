package com.nutrili.misc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Properties {
    @Value("${user.oauth.clientId}")
    private String user;

    @Value("${user.oauth.clientSecret}")
    private String password;

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone}")
    private String phone;
}
