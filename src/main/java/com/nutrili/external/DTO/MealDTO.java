package com.nutrili.external.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class MealDTO {

    @NotNull
    @Size(min=1)
    private String name;

    @NotNull
    @Size(min=1)
    private String category;

    @NotNull
    @Size(min=1)
    private String color;

}
