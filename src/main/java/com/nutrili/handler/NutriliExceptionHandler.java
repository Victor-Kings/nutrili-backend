package com.nutrili.handler;

import com.nutrili.exception.ExceptionDetails;
import com.nutrili.exception.InvalidTokenException;
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

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidTokenException(InvalidTokenException invalidTokenException)
    {
        exceptionDetails.setTitle("Aopa, your are not allowed here");
        exceptionDetails.setDetails(invalidTokenException.getMessage());
        exceptionDetails.setComplement(invalidTokenException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationError.INVALID_TOKEN.getCode());
    }

}
