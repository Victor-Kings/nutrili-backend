package com.nutrili.service;

import com.nutrili.exception.PhoneNotFoundException;
import com.nutrili.external.database.entity.SmsToken;
import com.nutrili.external.database.repository.SmsTokenRepository;
import com.nutrili.config.Properties;
import com.nutrili.Utils.Emotes;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.Twilio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SmsService {
    @Autowired
    Properties properties;

    @Autowired
    SmsTokenRepository smsTokenRepository;

    @Autowired
    UserService userService;

    private void send(String toPhone,String smsCode)
    {

        Twilio.init(properties.getAccountSid(),properties.getAuthToken());
        Message message = Message.creator(new PhoneNumber(toPhone), new PhoneNumber(properties.getPhone()), Emotes.thumbsUp+Emotes.meal+" Código de acesso Nutrili: "+smsCode)
                .create();
    }

    public void generateSmsToken(String phone)
    {
        if(!userService.getUserByPhone(phone)) {
            userService.insertUserByPhone(phone);
        }
        String smsCode= createSMSCode();
        smsTokenRepository.deleteOldTokens((System.currentTimeMillis()));
        deletePhoneByCode(phone);
        SmsToken smsToken = new SmsToken();
        smsToken.setCode(smsCode);
        smsToken.setNumber(phone);
        smsToken.setCreateTime((System.currentTimeMillis()));
        smsTokenRepository.save(smsToken);
        send("+55"+phone,smsCode);
    }

    private String createSMSCode() {
        return  RandomStringUtils.random(6, true, true).toUpperCase().toString();
    }

    public Optional <SmsToken> findPhoneCode(String phone, String code) {
        return smsTokenRepository.findByNumberAndCode(phone, code);
    }

    public void deletePhoneByCode(String phone)
    {
        smsTokenRepository.deleteTokenByPhone(phone);
    }

}
