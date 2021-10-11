package com.nutrili.service;

import com.nutrili.external.DTO.AnswerDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.QuestionAnswer;
import com.nutrili.external.database.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    public void insertAnswer(Patient patient, List<AnswerDTO> answerDTOList)
    {
        answerDTOList.forEach(answerDTO -> {
            QuestionAnswer answer = new QuestionAnswer();
            answer.setAnswer(answerDTO.getAnswer());
            answer.setIdQuestion(answerDTO.getIdQuestion());
            answer.setPatient(patient);
            answer.setDateOfAnswer(new Date());
            answerRepository.save(answer);

        });
    }

    public List<AnswerDTO> getAnswer(UUID id) {
        Optional<List<QuestionAnswer>> answerList = answerRepository.findQuestion(id);
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        if (answerList.isPresent()) {
            answerList.get().forEach(answer -> {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setAnswer(answer.getAnswer());
                answerDTO.setIdQuestion(answer.getIdQuestion());
                answerDTOList.add(answerDTO);
            });
        }
        return answerDTOList;
    }
}
