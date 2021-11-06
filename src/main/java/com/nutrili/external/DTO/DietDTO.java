package com.nutrili.external.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DietDTO {
    String name;
    List<String> food;
}
