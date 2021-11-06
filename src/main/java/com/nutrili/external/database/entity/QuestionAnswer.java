package com.nutrili.external.database.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="Answer")
public class QuestionAnswer {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idAnswer", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "patientId")
    private Patient patient;

    @Column(name = "idQuestion")
    @NotNull
    private int idQuestion;

    @Column(name= "answer")
    @NotEmpty
    private String answer;

    @Column(name="dateAnswer")
    @NotNull
    private Date DateOfAnswer;

}
