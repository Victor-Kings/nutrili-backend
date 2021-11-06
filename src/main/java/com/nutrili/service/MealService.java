package com.nutrili.service;

import com.nutrili.Utils.CategoriesMap;
import com.nutrili.external.DTO.MealChartDataDTO;
import com.nutrili.external.DTO.MealChartMobileDTO;
import com.nutrili.external.DTO.MealDTO;
import com.nutrili.external.database.entity.Meal;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public MealChartDataDTO getChart(UUID patientID){
        MealChartDataDTO mealChartDataDTOList = new MealChartDataDTO();
        List<Meal> mealList= mealRepository.recentMeal(patientID,new Date());
       mealList.stream().forEach((meal)->{
           if(mealChartDataDTOList.getCategory().stream().filter(mealChartDataDTO -> mealChartDataDTO.equals(meal.getCategory())).findFirst().isEmpty()) {
               mealChartDataDTOList.getCategory().add(meal.getCategory());
               mealChartDataDTOList.getCount().add((int) mealList.stream().filter(meal1 ->  meal1.getCategory().equals(meal.getCategory())).count());
           }
        });
       return mealChartDataDTOList;

    }

    public List<MealChartMobileDTO> getChartMobile(UUID patientID){
        List<MealChartMobileDTO> mealChartDataDTOList= new ArrayList<>();
        List<Meal> mealList= mealRepository.recentMeal(patientID,new Date());
        mealList.stream().forEach((meal)->{
            if(mealChartDataDTOList.stream().filter(mealChartDataDTO -> mealChartDataDTO.getCategory().equals(meal.getCategory())).findFirst().isEmpty()) {
                MealChartMobileDTO mealChartMobileDTO = new MealChartMobileDTO();
                mealChartMobileDTO.setCategory(meal.getCategory());
                if(CategoriesMap.categoriesMap.containsValue(meal.getCategory())) {
                    mealChartMobileDTO.setType((int) CategoriesMap.categoriesMap.getReverse(meal.getCategory()));
                } else {
                    mealChartMobileDTO.setType(11);
                }
                mealChartMobileDTO.setPercentage((double)mealList.stream().filter(meal1 ->  meal1.getCategory().equals(meal.getCategory())).count()*100/mealList.size());
                mealChartDataDTOList.add(mealChartMobileDTO);
            }
        });
        return mealChartDataDTOList;

    }

}
