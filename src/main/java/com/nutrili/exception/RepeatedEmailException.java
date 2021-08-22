package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class RepeatedEmailException extends RuntimeException{

    public RepeatedEmailException()
    {
        super(ApplicationExceptionEnum.REPEATED_EMAIL.getMessage());
    }

}
