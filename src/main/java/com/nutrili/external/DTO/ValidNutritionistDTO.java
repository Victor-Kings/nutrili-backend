package com.nutrili.external.DTO;

import lombok.Data;

@Data
public class ValidNutritionistDTO {
    private Boolean nutritionist;

    public ValidNutritionistDTO() {
        nutritionist=true;
    }
}
