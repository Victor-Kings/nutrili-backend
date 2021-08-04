package com.nutrili.handler;

import com.nutrili.exception.ExceptionDetails;
import com.nutrili.exception.models.ApplicationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDate;


@ControllerAdvice
public class NutriliExceptionHandler {


    @Autowired ExceptionDetails exceptionDetails;

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidTokenException(UsernameNotFoundException usernameNotFoundException)
    {
        exceptionDetails.setTitle("Invalid Token Exception");
        exceptionDetails.setDetails(usernameNotFoundException.getMessage());
        exceptionDetails.setComplement(usernameNotFoundException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationError.INVALID_CREDENTIALS.getCode());
    }

}
