package com.nutrili.external.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DashboardDataDTO {
    private List<NutritionistRequestDTO> nutritionistDTOList;
    private int numberOfPatient;
    private int numberOfPendingRequest;
}
