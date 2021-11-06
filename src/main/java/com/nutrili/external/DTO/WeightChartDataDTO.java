package com.nutrili.external.DTO;

import lombok.Data;

import java.util.List;

@Data
public class WeightChartDataDTO {
    private String label;
    private List<Double> data;
}
