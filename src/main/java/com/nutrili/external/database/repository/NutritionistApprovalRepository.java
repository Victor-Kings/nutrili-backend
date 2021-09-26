package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.NutritionistApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NutritionistApprovalRepository extends JpaRepository<NutritionistApproval,Long> {
    @Query("select na from NutritionistApproval na where :currentTime - na.dateOfRequest < '28 days' and na.approval is null and na.patient.id=:patientID")
    List<NutritionistApproval> findRecentRequest(@Param("patientID") long patientID,@Param("currentTime") Date currentTime);

    @Query("select na from NutritionistApproval na where :currentTime - na.dateOfRequest < '28 days' and na.approval is null and na.nutritionist.id=:nutritionistID")
    List<NutritionistApproval> findRequestBynutritionist(@Param("nutritionistID") long nutritionistID,@Param("currentTime") Date currentTime);

}
