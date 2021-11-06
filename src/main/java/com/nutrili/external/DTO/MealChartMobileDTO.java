package com.nutrili.external.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MealChartMobileDTO {
    private String category;
    private Double percentage;
    private int type;
}
