package com.nutrili.external.DTO;

import com.nutrili.external.database.entity.Address;
import lombok.Data;

@Data
public class NutritionistInfoDTO {
    private String name;
    private String crnType;
    private int score;
    private String profilePic;
    private String birth;
    private String phone;
    private int numberOfPatients;
    private Address office;
}
