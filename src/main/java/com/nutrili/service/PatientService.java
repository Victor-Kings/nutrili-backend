package com.nutrili.service;

import com.nutrili.external.DTO.PatientDTO;
import com.nutrili.external.DTO.PatientProfileDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public PatientProfileDTO getProfile(UUID patientID) {
        Patient patient =patientRepository.findById(patientID).get();
        PatientProfileDTO profileDTO= new PatientProfileDTO();
        profileDTO.setBirth(patient.getBirth().toString());
        profileDTO.setGender(patient.getGender());
        profileDTO.setAddress(patient.getAddressId());
        profileDTO.setHeight(patient.getHeight());
        profileDTO.setWeight(patient.getWeight());
        profileDTO.setName(patient.getName());
        profileDTO.setProfilePic(patient.getImage());
        profileDTO.setAge((int) (TimeUnit.DAYS.convert(new Date().getTime() -patient.getBirth().getTime(),TimeUnit.MILLISECONDS)/365));
        return profileDTO;
    }
}
