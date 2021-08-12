package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException()
    {
        super(ApplicationExceptionEnum.INVALID_CREDENTIALS.getMessage());
    }

}
