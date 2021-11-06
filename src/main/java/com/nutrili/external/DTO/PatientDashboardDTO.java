package com.nutrili.external.DTO;

import com.nutrili.external.DTO.MeasureDTO;
import com.nutrili.external.DTO.WeightHistoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class PatientDashboardDTO {
    WeightHistoryDTO weightHistoryChart;
    MeasureDTO measure;
    PatientDTO patient;
    MealChartDataDTO mealChartDataDTO;
    List<DietDTO> diet;
}
