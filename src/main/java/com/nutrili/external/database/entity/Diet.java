package com.nutrili.external.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="DIET")
public class Diet {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDiet", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    @JsonIgnore
    private UUID id;

    @NotNull
    @Column(name= "email")
    private String category;

    @NotNull
    @Column(name= "foods")
    private String foods;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "patient")
    private Patient patient;

}
