package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.MealDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    MealService mealService;

    @PostMapping("/mealTime")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity requestNutritionist(@Valid @RequestBody List<MealDTO> mealDTOList){
        mealService.insertMeal((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),mealDTOList);
        return ResponseEntity.ok("meal was recorded");
    }

    @GetMapping("/mealChart")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity mealChart(){
        return ResponseEntity.ok(mealService.getChartMobile(((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

}
