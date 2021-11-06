package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query("select p from Patient p where p.id=:patientID and p.nutritionist is not null")
    Optional<Patient> findPatientWithNutritionist(@Param("patientID") UUID patientID);

    @Query("select p from Patient p where p.nutritionist.id=:nutritionistID")
    List<Patient> findPatientByNutritionist(@Param("nutritionistID") UUID nutritionistID);

    @Query("select p from Patient p where p.nutritionist.id=:nutritionistID")
    Page<Patient> patientSearch(@Param("nutritionistID") UUID nutritionistID,Pageable pageable);

    @Query("select p from Patient p where p.nutritionist.id=:nutritionistID and upper(p.name) like upper(:name)")
    Page<Patient> patientSearchByName(@Param("nutritionistID") UUID nutritionistID,Pageable pageable, @Param("name") String name);

}
