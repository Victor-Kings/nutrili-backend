package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.WeightHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WeightHistoryRepository extends JpaRepository<WeightHistory, UUID> {

    @Query("select wh from WeightHistory wh where wh.patient.id= :patientID order by wh.dateOfWeighing")
    List<WeightHistory> getWeightList(@Param("patientID") UUID patientID, Pageable pageable);

}
