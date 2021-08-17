package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
