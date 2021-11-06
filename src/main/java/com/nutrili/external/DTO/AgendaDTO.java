package com.nutrili.external.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AgendaDTO {
    List<AppointmentDTO> morningAppointments;
    List<AppointmentDTO> afternoonAppointments;
}
