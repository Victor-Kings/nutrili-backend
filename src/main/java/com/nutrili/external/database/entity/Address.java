package com.nutrili.external.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="ADDRESS")
public class Address {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAddress", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    @JsonIgnore
    private UUID id;

    @Pattern(regexp="^[0-9]{5}-[0-9]{3}")
    @Column(name = "CEP")
    @Size(min=9,max = 9)
    private String cep;

    @Column(name="stateAddress")
    @Size(min=2,max=2)
    private String state;

    @Column(name = "city")
    @Size(min= 1)
    private String city;

    @Column(name= "neighborhood")
    @Size(min= 1)
    private String neighborhood;

    @Column(name= "street")
    @Size(min=1)
    private String street;

    @Column(name= "number")
    @Size(min=1)
    private String number;

    public Address(String cep, String state, String city, String neighborhood, String street, String number) {
        this.cep = cep;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
    }

    public Address() {
    }
}
