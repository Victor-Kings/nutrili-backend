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
    @NotNull
    private int score;

    @Column(name= "CRNType")
    @NotNull
    private String crnType;

    @OneToMany(mappedBy="nutritionist",cascade={CascadeType.PERSIST})
    private List<Patient> patientList;

}
