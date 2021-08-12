package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException()
    {
        super(ApplicationExceptionEnum.INVALID_TOKEN.getMessage());
    }
}
