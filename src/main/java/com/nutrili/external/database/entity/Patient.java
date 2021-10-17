package com.nutrili.external.database.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Patient")
public class Patient extends User{

    @Column(name="height")
    @DecimalMin(value = "1.00")
    @DecimalMax(value="200.00")
    private Double height;

    @Column(name="weight")
    @DecimalMin(value = "1.00")
    @DecimalMax(value="200.00")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "nutritionist")
    private Nutritionist nutritionist;

    @Column(name="status")
    private String status;

    @Column(name="lastMeeting")
    private Date dateOfLastMeeting;


}
