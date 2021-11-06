package com.nutrili.external.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MealChartMobileDTO {
    private List<String> category;
    private List<Double> percentage;

    public MealChartMobileDTO() {
        this.category = new ArrayList<>();
        this.percentage =  new ArrayList<>();
    }
}
