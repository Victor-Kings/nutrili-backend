package com.nutrili.external.DTO;

import lombok.Data;

import java.util.List;

@Data
public class WeightHistoryDTO {
    private List<String> dates;
    private WeightChartDataDTO chartData;
}
