package com.nutrili.external.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class NutritionistRequestDTO {
    private long requestId;
    private int age;
    private Date date;
    private String cpf;
    private String address;
    private MeasureDTO measure;

}
