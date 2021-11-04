package com.nutrili.external.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
public class Meal {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MealId", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    @NotNull
    @Size(min=1)
    private String name;

    @NotNull
    private Date dateOfMeal;

    @NotNull
    @Size(min=1)
    private String category;

    @NotNull
    @Size(min=1)
    private String color;

    @ManyToOne
    @JoinColumn(name = "patient")
    @NotNull
    private Patient patient;

}
