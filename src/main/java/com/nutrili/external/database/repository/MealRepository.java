package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.Meal;
import com.nutrili.external.database.entity.NutritionistApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {

    @Query("select m from Meal m where :currentTime - m.dateOfMeal < '28 days' and m.patient.id=:patientID")
    List<Meal> recentMeal(@Param("patientID") UUID patientID, @Param("currentTime") Date currentTime);

}
