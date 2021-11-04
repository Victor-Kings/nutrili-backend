package com.nutrili.service;

import com.nutrili.external.DTO.MealDTO;
import com.nutrili.external.database.entity.Meal;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    public void insertMeal(Patient patient, List<MealDTO> mealDTOList){
        mealDTOList.forEach(mealDTO -> {
            Meal meal = new Meal();
            meal.setDateOfMeal(new Date());
            meal.setColor(mealDTO.getColor());
            meal.setName(mealDTO.getName());
            meal.setCategory(mealDTO.getCategory());
            meal.setPatient(patient);
            mealRepository.save(meal);
        });
    }
}
