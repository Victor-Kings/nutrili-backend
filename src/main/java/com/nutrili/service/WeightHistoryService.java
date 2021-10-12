package com.nutrili.service;

import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.WeightHistory;
import com.nutrili.external.database.repository.WeightHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WeightHistoryService {
    @Autowired
    WeightHistoryRepository weightHistoryRepository;

    public void insertWeightHistory(double weight, Patient patient) {
        WeightHistory weightHistory = new WeightHistory();
        weightHistory.setWeight(weight);
        weightHistory.setPatient(patient);
        weightHistory.setDateOfWeighing(new Date());
        weightHistoryRepository.save(weightHistory);
    }
}
