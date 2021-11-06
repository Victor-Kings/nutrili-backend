package com.nutrili.service;

import com.nutrili.exception.InvalidTokenException;
import com.nutrili.config.Properties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ValidateTokenService {

    @Autowired
    Logger logger;

    @Autowired
    Properties properties;

    public void validateToken(String token) throws InvalidTokenException {
        if(!token.startsWith("Aoba "))
        {
            throw new InvalidTokenException();
        }
        decodeToken(token.replace("Aoba ",""));
    }

    private void decodeToken(String token)
    {
        String decodedString = new String(Base64.getDecoder().decode(token));
        List<String> data = Stream.of(decodedString.split(":")).map(elem -> new String(elem)).collect(Collectors.toList());
        if(!data.get(0).equals(properties.getUser()) || !data.get(1).equals(properties.getPassword()))
        {
            throw new InvalidTokenException();
        }
    }


}
