package com.nutrili.external.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
public class NotificationDTO {

    @NotNull
    private UUID id;

    @NotNull
    private int index;

    @NotNull
    private String senderName;

    @NotNull
    private String message;

    @NotNull
    private String dateOfNotification;

    @NotNull
    private Boolean status;

}
