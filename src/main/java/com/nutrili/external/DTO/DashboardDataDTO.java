package com.nutrili.external.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DashboardDataDTO {
    @JsonProperty("nutritionistList")
    private List<NutritionistRequestDTO> nutritionistDTOList;
    private int numberOfPatient;
    private int numberOfPendingRequest;
}
