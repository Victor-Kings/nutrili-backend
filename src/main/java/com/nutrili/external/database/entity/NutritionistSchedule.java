package com.nutrili.external.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class NutritionistSchedule {

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

    @NotNull
    private String title;

    @NotNull
    private String summary;

    @NotNull
    private String beginingOfMeeting;


}
