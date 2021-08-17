package com.nutrili.external.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "Nutritionist")
public class Nutritionist extends User{

    @Column(name = "CRN")
    @NotNull
    private int crn;

    @Column(name = "score")
    @NotNull
    private int score;

}
