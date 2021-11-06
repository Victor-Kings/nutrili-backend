package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class RepeatedPhoneException extends RuntimeException{

    public RepeatedPhoneException()
    {
        super(ApplicationExceptionEnum.REPEATED_PHONE.getMessage());
    }

}
