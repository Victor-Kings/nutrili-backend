package com.nutrili.external.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDTO {
    @NotNull
    private int idQuestion;
    @NotNull
    private String answer;

    private String question;
}
