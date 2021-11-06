package com.nutrili.external.DTO;

import com.nutrili.external.database.entity.Address;
import lombok.Data;

@Data
public class PatientProfileDTO {
    private String name;
    private String gender;
    private String birth;
    private double weight;
    private double height;
    private int age;
    private Address address;
    private String profilePic;
}
