package com.nutrili.external.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Notification")
public class Notification {

    @Column(name = "notificationId")
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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
