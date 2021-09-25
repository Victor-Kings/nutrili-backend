package com.nutrili.external.DTO;

import lombok.Data;

@Data
public class NutritionistDTO {
    private long id;
    private int score;
    private String name;
    private String city;
    private String state;
    private String profilePicture;
    private String phone;
}
