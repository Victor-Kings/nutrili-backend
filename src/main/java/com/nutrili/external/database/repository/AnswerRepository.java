package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<QuestionAnswer,UUID> {
    @Query("select q from QuestionAnswer q where q.patient.id =:patientID and q.idQuestion>=11")
    Optional<List<QuestionAnswer>> findQuestion(@Param("patientID") UUID patientID);

    @Query("select q from QuestionAnswer q where q.patient.id =:patientID and q.idQuestion!=1 and q.idQuestion!=2 and q.idQuestion!=3 and q.idQuestion!=4 and q.idQuestion!=7 order by idQuestion asc")
    Optional<List<QuestionAnswer>> findQuestionForDashboard(@Param("patientID") UUID patientID);
}
