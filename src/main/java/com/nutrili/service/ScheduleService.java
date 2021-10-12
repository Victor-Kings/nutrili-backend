package com.nutrili.service;

import com.nutrili.external.DTO.AppointmentDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.NutritionistSchedule;
import com.nutrili.external.database.repository.NutritionistScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    NutritionistScheduleRepository nutritionistScheduleRepository;

    public void insertAppointment(AppointmentDTO appointmentDTO, Nutritionist nutritionist){
        NutritionistSchedule nutritionistSchedule = new NutritionistSchedule();
        nutritionistSchedule.setNutritionist(nutritionist);
        nutritionistSchedule.setEndingDate(appointmentDTO.getEndingDate());
        nutritionistSchedule.setEveryWeek(appointmentDTO.isEveryWeek());
        nutritionistSchedule.setStartingDate(appointmentDTO.getStartingDate());
        nutritionistSchedule.setStartingTime(appointmentDTO.getStartingTime());
        nutritionistSchedule.setTitle(appointmentDTO.getTitle());
        nutritionistSchedule.setSummary(appointmentDTO.getSummary());
        nutritionistSchedule.setEndingTime(appointmentDTO.getEndingTime());
        nutritionistScheduleRepository.save(nutritionistSchedule);

    }

    public void deleteAppointment(UUID appointmentId){
        nutritionistScheduleRepository.deleteAppointmentById(appointmentId);
    }
}
