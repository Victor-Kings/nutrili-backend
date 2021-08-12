package com.nutrili.exception.ExceptionModel;


import org.springframework.http.HttpStatus;

public enum ApplicationExceptionEnum {
    INVALID_CREDENTIALS(HttpStatus.NOT_ACCEPTABLE, "The user credential are invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token provided is not valid"),
    INVALID_PHONE(HttpStatus.NOT_ACCEPTABLE,"Phone provided is not registered");

    private final HttpStatus code;
    private final String message;

    ApplicationExceptionEnum(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {return code;}

    public  String getMessage() { return  message;}

}
