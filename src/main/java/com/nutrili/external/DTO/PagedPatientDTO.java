package com.nutrili.external.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PagedPatientDTO {
    private List<PatientDTO> patientDTOList;
    private boolean lastPage;
    private int numberOfPages;
    private boolean firstPage;

}
