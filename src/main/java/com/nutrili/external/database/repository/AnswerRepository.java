package com.nutrili.external.database.repository;

import com.nutrili.external.database.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<QuestionAnswer,Long> {
}
