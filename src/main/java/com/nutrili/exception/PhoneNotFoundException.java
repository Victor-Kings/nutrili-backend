package com.nutrili.exception;

import com.nutrili.exception.models.ApplicationError;

public class PhoneNotFoundException extends RuntimeException{

    public PhoneNotFoundException()
    {
        super(ApplicationError.INVALID_PHONE.getMessage());
    }
}
