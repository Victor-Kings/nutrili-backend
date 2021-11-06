package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class InvalidCpfException extends RuntimeException{
    public InvalidCpfException(){
        super(ApplicationExceptionEnum.INVALID_CPF.getMessage());
    }
}
