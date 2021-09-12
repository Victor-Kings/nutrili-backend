package com.nutrili.external.DTO;

import lombok.Data;

@Data
public class NewUserDTO {
    private boolean newUser;

    public NewUserDTO() {
        newUser=true;
    }
}
