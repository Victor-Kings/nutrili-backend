package com.nutrili.exception.ExceptionModel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExceptionDetails {
    private String title;
    private String details;
    private Throwable complement;
    private LocalDate timeException;
}
