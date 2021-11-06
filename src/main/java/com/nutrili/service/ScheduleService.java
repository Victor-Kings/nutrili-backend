package com.nutrili.service;

import com.nutrili.exception.SomethingWentWrongException;
import com.nutrili.external.DTO.AgendaDTO;
import com.nutrili.external.DTO.AppointmentDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.NutritionistSchedule;
import com.nutrili.external.database.repository.NutritionistScheduleRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private void ScheduleToAppointmentDTO(NutritionistSchedule nutritionistSchedule,AppointmentDTO appointmentDTO){
            appointmentDTO.setEndingDate(nutritionistSchedule.getEndingDate());
            appointmentDTO.setEndingTime(nutritionistSchedule.getEndingTime());
            appointmentDTO.setEveryWeek(nutritionistSchedule.isEveryWeek());
            appointmentDTO.setSummary(nutritionistSchedule.getSummary());
            appointmentDTO.setStartingDate(nutritionistSchedule.getStartingDate());
            appointmentDTO.setTitle(nutritionistSchedule.getTitle());
            appointmentDTO.setId(nutritionistSchedule.getId());
            appointmentDTO.setStartingTime(nutritionistSchedule.getStartingTime());
    }

    public AgendaDTO getAppointment(Nutritionist nutritionist){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        List<AppointmentDTO> morningAppointments = new ArrayList<>();
        List<AppointmentDTO> afternoonAppointments= new ArrayList<>();
        AgendaDTO agendaDTO = new AgendaDTO();
        nutritionistScheduleRepository.getSchedule(nutritionist.getId(),dateFormat.format(new Date())).stream()
                .distinct()
                .collect(Collectors.toList())
                .forEach(nutritionistSchedule -> {
            try {
                AppointmentDTO appointmentDTO = new AppointmentDTO();
                ScheduleToAppointmentDTO(nutritionistSchedule,appointmentDTO);
                int time = timeFormat.parse(nutritionistSchedule.getStartingTime()).getHours();
                if(time>5 && time<=12){
                    morningAppointments.add(appointmentDTO);
                } else {
                    afternoonAppointments.add(appointmentDTO);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
        agendaDTO.setAfternoonAppointments(afternoonAppointments);
        agendaDTO.setMorningAppointments(morningAppointments);
        return agendaDTO;

    }

    public void updateAppointment(AppointmentDTO appointmentDTO){
        nutritionistScheduleRepository.findById(appointmentDTO.getId()).ifPresentOrElse(nutritionistSchedule -> {
            nutritionistSchedule.setTitle(appointmentDTO.getTitle());
            nutritionistSchedule.setSummary(appointmentDTO.getSummary());
            nutritionistSchedule.setStartingTime(appointmentDTO.getStartingTime());
            nutritionistSchedule.setEndingTime(appointmentDTO.getEndingTime());
            nutritionistSchedule.setEndingDate(appointmentDTO.getEndingDate());
            nutritionistSchedule.setEveryWeek(appointmentDTO.isEveryWeek());
            nutritionistSchedule.setStartingDate(appointmentDTO.getStartingDate());
            nutritionistScheduleRepository.save(nutritionistSchedule);
        },()->{
            throw new SomethingWentWrongException();
        });

    }

}
