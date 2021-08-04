package com.nutrili.exception;

import com.nutrili.exception.models.ApplicationError;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException()
    {
        super(ApplicationError.INVALID_CREDENTIALS.getMessage());
    }

}
