package com.nutrili.external.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="DATASET")
public class Dataset {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDataset", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    @JsonIgnore
    private UUID id;

    private String name;

    private String englishName;

    private String groupName;

    private String color;
}
