package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.NutritionistSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface NutritionistScheduleRepository extends JpaRepository<NutritionistSchedule, UUID> {
    @Modifying
    @Query("delete from NutritionistSchedule ns where ns.id= :scheduleId")
    void deleteAppointmentById(@Param("scheduleId") UUID scheduleId);
}
