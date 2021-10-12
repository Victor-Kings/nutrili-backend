package com.nutrili.external.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class NutritionistRequestDTO {
    private UUID requestId;
    private int age;
    private String date;
    private String name;
    private String cpf;
    private String address;
    private MeasureDTO measure;
    private List<AnswerDTO> answerList;

}
