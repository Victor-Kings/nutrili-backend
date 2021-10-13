package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.NutritionistSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
public interface NutritionistScheduleRepository extends JpaRepository<NutritionistSchedule, UUID> {
    @Modifying
    @Query("delete from NutritionistSchedule ns where ns.id= :scheduleId")
    void deleteAppointmentById(@Param("scheduleId") UUID scheduleId);

    @Query("select ns from NutritionistSchedule ns where ns.nutritionist.id= :nutritionistId and to_date(ns.startingDate,'DD/MM/YYYY')=to_date(:currentDate,'DD/MM/YYYY')"+
    " or ((date_part('dow',to_date(ns.startingDate,'DD/MM/YYYY'))) = (date_part('dow',to_date(:currentDate,'DD/MM/YYYY'))) and ns.everyWeek=true) ")
    List<NutritionistSchedule> getSchedule(@Param("nutritionistId") UUID nutritionistId, @Param("currentDate") String currentDate);


}
