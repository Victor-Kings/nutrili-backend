package com.nutrili.exception;

import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;

public class InvalidNutritionistRequest extends RuntimeException{
    public InvalidNutritionistRequest() {
        super(ApplicationExceptionEnum.INVALID_NUTRITIONIST_REQUEST.getMessage());
    }
}
