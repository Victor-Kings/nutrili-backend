package com.nutrili.exception.ExceptionModel;


import org.springframework.http.HttpStatus;

public enum ApplicationExceptionEnum {
    INVALID_CREDENTIALS(HttpStatus.NOT_ACCEPTABLE, "The user credential are invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token provided is not valid"),
    INVALID_PHONE(HttpStatus.NOT_ACCEPTABLE,"Phone provided is not registered"),
    REPEATED_PHONE(HttpStatus.NOT_ACCEPTABLE,"Phone is already present in DB"),
    REPEATED_EMAIL(HttpStatus.NOT_ACCEPTABLE,"email is already present in DB");

    private final HttpStatus code;
    private final String message;

    ApplicationExceptionEnum(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {return code;}

    public  String getMessage() { return  message;}

}
