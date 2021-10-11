package com.nutrili.external.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "NutritionistApproval")
public class NutritionistApproval {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "nutritionist")
    private Nutritionist nutritionist;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "patient")
    private Patient patient;

    private Boolean approval;

    @NotNull
    private Date dateOfRequest;
}
