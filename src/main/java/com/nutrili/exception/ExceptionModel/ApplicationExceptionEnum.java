package com.nutrili.exception.ExceptionModel;


import org.springframework.http.HttpStatus;

public enum ApplicationExceptionEnum {
    INVALID_CREDENTIALS(HttpStatus.NOT_ACCEPTABLE, "The user credential are invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token provided is not valid"),
    INVALID_PHONE(HttpStatus.NOT_ACCEPTABLE,"Phone provided is not registered"),
    REPEATED_PHONE(HttpStatus.NOT_ACCEPTABLE,"Phone is already present in DB"),
    REPEATED_EMAIL(HttpStatus.NOT_ACCEPTABLE,"email is already present in DB"),
    INVALID_NUTRITIONIST_REQUEST(HttpStatus.NOT_ACCEPTABLE,"you have already made a request within 30 days to a nutritionist which hasn't been replied yet"),
    SOMETHING_WENT_WRONG(HttpStatus.INTERNAL_SERVER_ERROR,"something went wrong "),
    INVALID_CRN(HttpStatus.NOT_ACCEPTABLE,"Wrong combination of name and crn");

    private final HttpStatus code;
    private final String message;

    ApplicationExceptionEnum(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {return code;}

    public  String getMessage() { return  message;}

}
