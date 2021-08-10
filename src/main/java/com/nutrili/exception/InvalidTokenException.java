package com.nutrili.exception;

import com.nutrili.exception.models.ApplicationError;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException()
    {
        super(ApplicationError.INVALID_TOKEN.getMessage());
    }
}
