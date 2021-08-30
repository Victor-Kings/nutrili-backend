package com.nutrili.external.database.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="Answer")
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Setter(value= AccessLevel.NONE)
    @Column(name="idAnswer")
    private Long id;

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
