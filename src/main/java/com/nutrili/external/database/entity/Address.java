package com.nutrili.external.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name="ADDRESS")
public class Address {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAddress")
    @Setter(value= AccessLevel.NONE)
    private int id;

    @Column(name = "CEP")
    @NotNull
    @Pattern(regexp="^[0-9]{5}-[0-9]{3}")
    @Size(min=9,max = 9)
    private String cep;

    @Column(name="stateAddress")
    @NotNull
    @Size(min=2,max=2)
    private String state;

    @Column(name = "city")
    @Size(min= 1)
    @NotNull
    private String city;

    @Column(name= "neighborhood")
    @Size(min= 1)
    @NotNull
    private String neighborhood;

    @Column(name= "street")
    @Size(min=1)
    @NotNull
    private String street;

    @Column(name= "number")
    @Size(min=1)
    @NotNull
    private String number;

}
