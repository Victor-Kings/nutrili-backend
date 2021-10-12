package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.AppointmentDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.User;
import com.nutrili.service.NotificationService;
import com.nutrili.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/insertAppointment")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> insertAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO){
        scheduleService.insertAppointment(appointmentDTO,(Nutritionist) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        notificationService.createNotification((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(),"Sistema","Compromisso agendado.");
        return ResponseEntity.ok("appointment was scheduled successfully");
    }

    @DeleteMapping("/removeAppointment")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> deleteAppointment(@RequestParam @NotNull UUID appointmentId) {
        scheduleService.deleteAppointment(appointmentId);
        return ResponseEntity.ok("appointment was removed from your agenda successfully");
    }
}
