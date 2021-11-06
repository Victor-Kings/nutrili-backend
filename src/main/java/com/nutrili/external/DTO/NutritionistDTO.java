package com.nutrili.external.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class NutritionistDTO {
    private UUID id;
    private int score;
    private String name;
    private String city;
    private String state;
    private String profilePicture;
    private String phone;
}
