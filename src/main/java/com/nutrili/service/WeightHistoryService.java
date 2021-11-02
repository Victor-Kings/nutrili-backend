package com.nutrili.service;

import com.nutrili.external.DTO.WeightChartDataDTO;
import com.nutrili.external.DTO.WeightHistoryDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.WeightHistory;
import com.nutrili.external.database.repository.WeightHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public WeightHistoryDTO getWeightChartData(UUID patientID){

        WeightHistoryDTO weightHistoryDTO = new WeightHistoryDTO();
        WeightChartDataDTO weightChartDataDTO= new WeightChartDataDTO();
        List<String> dates = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        weightHistoryRepository.getWeightList(patientID, PageRequest.of(0, 5)).forEach(weight->{
            dates.add(weight.getDateOfWeighing().toString());
            data.add(weight.getWeight());
        });
        weightChartDataDTO.setData(data);
        weightChartDataDTO.setLabel("WeightChart");

        weightHistoryDTO.setChartData(weightChartDataDTO);
        weightHistoryDTO.setDates(dates);

        return weightHistoryDTO;

    }
}
