package com.nutrili.external.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class NutritionistRequestDTO {
    private UUID requestId;
    private int age;
    private Date date;
    private String name;
    private String cpf;
    private String address;
    private MeasureDTO measure;
    private List<AnswerDTO> answerList;

}
