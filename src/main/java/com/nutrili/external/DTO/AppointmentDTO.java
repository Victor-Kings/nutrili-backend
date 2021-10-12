package com.nutrili.external.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppointmentDTO {

    @NotNull
    private String title;

    private String summary;

    @NotNull
    private String startingDate;

    @NotNull
    private String endingDate;

    @NotNull
    private String startingTime;

    @NotNull
    private String endingTime;

    private boolean everyWeek;

}
