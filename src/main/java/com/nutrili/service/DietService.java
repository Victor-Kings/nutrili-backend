package com.nutrili.service;

import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.DTO.DietDTO;
import com.nutrili.external.database.entity.Diet;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DietService {

    @Autowired
    DietRepository dietRepository;

    @Autowired
    NutriliUserDetailsService nutriliUserDetailsService;

    public void updateDiet(UUID patientID, List<DietDTO> dietDTOList){
        if(nutriliUserDetailsService.getUser(patientID).isPresent()){
            dietRepository.deleteOldDiet(patientID);
            dietDTOList.forEach(dietDTO->{
                Diet diet = new Diet();
                diet.setCategory(dietDTO.getName());
                diet.setPatient((Patient) nutriliUserDetailsService.getUser(patientID).get());
                diet.setFoods(dietDTO.getFood().stream().collect(Collectors.joining(";")));
                dietRepository.save(diet);
            });
        } else {
            throw new UserNotFoundException();
        }
    }
}
