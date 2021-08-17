package com.nutrili.service;

import com.nutrili.exception.PhoneNotFoundException;
import com.nutrili.external.database.entity.SmsToken;
import com.nutrili.external.database.repository.SmsTokenRepository;
import com.nutrili.config.Properties;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.Twilio;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
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
        byte[] emojiBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x8D, (byte)0xB2};
        String emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));

        byte[] emojiBytes2 = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x91, (byte)0x8d};
        String emojiAsString2 = new String(emojiBytes2, Charset.forName("UTF-8"));

        Twilio.init(properties.getAccountSid(),properties.getAuthToken());
        Message message = Message.creator(new PhoneNumber(toPhone), new PhoneNumber(properties.getPhone()), emojiAsString+emojiAsString2+" Código de acesso Nutrili: "+smsCode)
                .create();
    }

    public void generateSmsToken(String phone)
    {
        if(!userService.getUserByPhone(phone)) {
            throw new PhoneNotFoundException();
        }
        String smsCode= createSMSCode();
        smsTokenRepository.deleteOldTokens((System.currentTimeMillis()));
        deletePhoneByCode(phone);
        SmsToken smsToken = new SmsToken();
        smsToken.setCode(smsCode);
        smsToken.setNumber(phone);
        smsToken.setCreateTime((System.currentTimeMillis()));
        smsTokenRepository.save(smsToken);
        send("+"+phone,smsCode);
    }

    private String createSMSCode() {
        return  RandomStringUtils.random(8, true, true).toUpperCase().toString();
    }

    public Optional <SmsToken> findPhoneCode(String phone, String code) {
        return smsTokenRepository.findByNumberAndCode(phone, code);
    }

    public void deletePhoneByCode(String phone)
    {
        smsTokenRepository.deleteTokenByPhone(phone);
    }

}
