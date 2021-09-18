package com.nutrili.external.DTO;

import lombok.Data;

@Data
public class NewUserDTO {
    private boolean newUser;
    private boolean ancientPlusComplete;

    public NewUserDTO() {
        newUser=true;
        ancientPlusComplete=false;
    }
}
