package com.nutrili.external.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Notification")
public class Notification {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notificationId", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    @NotNull
    private String senderName;

    @NotNull
    private String message;

    @NotNull
    private Date dateOfNotification;

    @NotNull
    private Boolean status;

    @OneToOne
    @NotNull
    @JoinColumn(name = "receiverUser")
    @JsonIgnore
    User receiverUser;

}
