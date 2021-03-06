package com.nutrili.handler;

import com.nutrili.exception.*;
import com.nutrili.exception.ExceptionModel.ExceptionDetails;
import com.nutrili.exception.ExceptionModel.ApplicationExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.INVALID_TOKEN.getCode());
    }

    @ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerPhoneNotFoundException(PhoneNotFoundException phoneNotFoundException)
    {
        exceptionDetails.setTitle("Phone number received wasn't found");
        exceptionDetails.setDetails(phoneNotFoundException.getMessage());
        exceptionDetails.setComplement(phoneNotFoundException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.INVALID_PHONE.getCode());
    }

    @ExceptionHandler(RepeatedPhoneException.class)
    public ResponseEntity<ExceptionDetails> handlerRepeatedPhoneException(RepeatedPhoneException repeatedPhoneException)
    {
        exceptionDetails.setTitle("Duplicate phone");
        exceptionDetails.setDetails(repeatedPhoneException.getMessage());
        exceptionDetails.setComplement(repeatedPhoneException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.REPEATED_PHONE.getCode());
    }

    @ExceptionHandler(RepeatedEmailException.class)
    public ResponseEntity<ExceptionDetails> handlerRepeatedEmailException(RepeatedEmailException repeatedEmailException)
    {
        exceptionDetails.setTitle("Duplicate email");
        exceptionDetails.setDetails(repeatedEmailException.getMessage());
        exceptionDetails.setComplement(repeatedEmailException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.REPEATED_PHONE.getCode());
    }

    @ExceptionHandler(InvalidNutritionistRequest.class)
    public ResponseEntity<ExceptionDetails> handlerRepeatedInvalidNutritionistRequestException(InvalidNutritionistRequest invalidNutritionistRequest)
    {
        exceptionDetails.setTitle("Invalid nutritionist request");
        exceptionDetails.setDetails(invalidNutritionistRequest.getMessage());
        exceptionDetails.setComplement(invalidNutritionistRequest.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.INVALID_NUTRITIONIST_REQUEST.getCode());
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    public ResponseEntity<ExceptionDetails> handlerSomethingWentWrongException(SomethingWentWrongException somethingWentWrongException)
    {
        exceptionDetails.setTitle("Internal Server Error");
        exceptionDetails.setDetails(somethingWentWrongException.getMessage());
        exceptionDetails.setComplement(somethingWentWrongException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.SOMETHING_WENT_WRONG.getCode());
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidCrnException(InvalidCpfException invalidCpfException)
    {
        exceptionDetails.setTitle("Internal CPF");
        exceptionDetails.setDetails(invalidCpfException.getMessage());
        exceptionDetails.setComplement(invalidCpfException.getCause());
        exceptionDetails.setTimeException(LocalDate.now());

        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ApplicationExceptionEnum.INVALID_CPF.getCode());
    }

}
