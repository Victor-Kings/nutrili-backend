package com.nutrili.external.DTO;

import lombok.Data;

@Data
public class NewUserDTO {
    private boolean newUser;
    private boolean ancientPlusComplete;
    private boolean ableToSearchNutritionist;

    public NewUserDTO() {
        newUser=true;
        ancientPlusComplete=false;
        ableToSearchNutritionist=true;
    }
}
