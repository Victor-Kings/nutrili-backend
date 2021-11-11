package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.MealDTO;
import com.nutrili.external.database.entity.Dataset;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.DatasetRepository;
import com.nutrili.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    MealService mealService;

    @Autowired
    DatasetRepository datasetRepository;

    @PostMapping("/mealTime")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity requestNutritionist(@Valid @RequestBody List<String> mealList){
        List<MealDTO> mealDTOList = new ArrayList<>();
        mealList.forEach(meal->{
            MealDTO mealDTO = new MealDTO();
           Optional<Dataset> mealOptional= datasetRepository.findByName(meal);
           if(mealOptional.isPresent()){
               mealDTO.setName(meal);
               mealDTO.setCategory(mealOptional.get().getGroupName());
               mealDTO.setColor(mealOptional.get().getColor());
           } else {
               mealOptional= datasetRepository.findByNameLike(meal);
               if(mealOptional.isPresent()) {
                   mealDTO.setName(meal);
                   mealDTO.setCategory(mealOptional.get().getGroupName());
                   mealDTO.setColor(mealOptional.get().getColor());
               } else {
                   mealDTO.setName(meal);
                   mealDTO.setCategory("");
                   mealDTO.setColor("");
               }
           }
            mealDTOList.add(mealDTO);
        });
        mealService.insertMeal((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),mealDTOList);
        return ResponseEntity.ok("meal was recorded");
    }

    @GetMapping("/mealChart")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity mealChart(){
        return ResponseEntity.ok(mealService.getChartMobile(((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

}
