package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class PhoneNotFoundException extends RuntimeException{

    public PhoneNotFoundException()
    {
        super(ApplicationExceptionEnum.INVALID_PHONE.getMessage());
    }
}
