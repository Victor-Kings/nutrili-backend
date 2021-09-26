package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select p from Patient p where p.id=:patientID and p.nutritionist is not null")
    Optional<Patient> findPatientWithNutritionist(@Param("patientID") long patientID);

}
