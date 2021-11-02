package com.nutrili.external.DTO;

import com.nutrili.external.DTO.MeasureDTO;
import com.nutrili.external.DTO.WeightHistoryDTO;
import lombok.Data;

@Data
public class PatientDashboardDTO {
    WeightHistoryDTO weightHistoryChart;
    MeasureDTO measure;
    PatientDTO patient;
}
