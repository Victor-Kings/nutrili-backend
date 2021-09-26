package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<QuestionAnswer,Long> {
    @Query("select q from QuestionAnswer q where q.patient.id =:patientID and q.idQuestion=11")
    Optional<QuestionAnswer> findQuestion(@Param("patientID") long patiendID);
}
