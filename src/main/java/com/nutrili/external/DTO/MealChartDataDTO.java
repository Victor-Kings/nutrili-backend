package com.nutrili.external.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MealChartDataDTO {
    private List<String> category;
    private List<Integer> count;

    public MealChartDataDTO() {
        this.category = new ArrayList<>();
        this.count =  new ArrayList<>();
    }
}
