package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
@Transactional
public interface DietRepository extends JpaRepository<Diet, UUID> {

    @Modifying
    @Query("delete from Diet d where d.patient.id= :patientID")
    void deleteOldDiet(@Param("patientID") UUID patientID);

    @Query("select d from Diet d where d.patient.id= :patientID")
    List<Diet> findByPatient(@Param("patientID") UUID patientID);
}
