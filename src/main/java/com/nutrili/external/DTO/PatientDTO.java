package com.nutrili.external.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class PatientDTO {
    private UUID patientID;
    private String profileIcon;
    private String name;
    private String status;
    private String dateOfLastMeeting;
}
