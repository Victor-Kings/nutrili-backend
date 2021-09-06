package com.nutrili.service;

import com.nutrili.config.Properties;
import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.database.entity.SmsToken;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.UserRepository;
import com.twilio.twiml.voice.Sms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutriliUserDetailsService extends UserService implements UserDetailsService {

    @Autowired
    SmsService smsService;

    @Autowired
    Properties properties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username==null || username.equals(""))
            throw new RuntimeException("Unhandled Exception");

        List<String> userData = Arrays.stream(StringUtils.split(
                username, ":")).collect(Collectors.toList());

        User user = null;
        if(userData.size()>1) {
            if (Integer.parseInt(userData.get(1)) == 1) {
                Optional<User> userValidation = userRepository.findByEmail(userData.get(0));
                if (userValidation.isPresent())
                    user = userValidation.get();
            } else {
                Optional<SmsToken> smsToken = smsService.findPhoneCode(userData.get(0), userData.get(2));
                if (smsToken.isPresent()) {
                    Optional<User> userValidation = userRepository.findByPhone(userData.get(0));
                    if (userValidation.isPresent()) {
                        user = userValidation.get();
                        user.setPassword(passwordEncoder.encode(properties.getTwilioPassword()));
                    }
                    smsService.deletePhoneByCode(userData.get(0));
                }
            }
        } else {
                Optional<User>userValidation = userRepository.findByEmail(userData.get(0));
                if (userValidation.isPresent())
                    user = userValidation.get();
                else {
                    userValidation = userRepository.findByPhone(userData.get(0));
                    if (userValidation.isPresent())
                        user = userValidation.get();
                }
        }

        if(user==null)
        {
            throw new UserNotFoundException();
        }

        return user;

    }
}
