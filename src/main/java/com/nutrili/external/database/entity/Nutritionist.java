package com.nutrili.external.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Nutritionist")
public class Nutritionist extends User{

    @Column(name = "CRN")
    @NotNull
    private String crn;

    @Column(name = "score")
    private int score;

    @Column(name= "CRNType")
    @NotNull
    private String crnType;

    @OneToMany(mappedBy="nutritionist",cascade={CascadeType.ALL})
    private List<Patient> patientList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "officeId")
    private Address officeId;

}
