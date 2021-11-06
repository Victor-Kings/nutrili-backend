package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class SomethingWentWrongException extends RuntimeException{
    public  SomethingWentWrongException(){
        super(ApplicationExceptionEnum.SOMETHING_WENT_WRONG.getMessage());
    }
}
