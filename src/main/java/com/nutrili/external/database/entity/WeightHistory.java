package com.nutrili.external.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class WeightHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    @NotNull
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient")
    @NotNull
    private Patient patient;

    @NotNull
    private Date dateOfWeighing;

    @NotNull
    private double weight;
}
