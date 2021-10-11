package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class InvalidCrnException extends RuntimeException{
    public InvalidCrnException(){
        super(ApplicationExceptionEnum.INVALID_CRN.getMessage());
    }
}
