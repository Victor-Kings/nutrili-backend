package com.nutrili.external.DTO;

import com.nutrili.external.database.entity.Address;
import lombok.Data;

@Data
public class NutritionistInfoDTO {
    private String name;
    private String crnType;
    private int score;
    private String profilePic;
    private int age;
    private String phone;
    private String office;
    private int numberOfPatients;
    private String officeName;
    private String officePhone;
}
