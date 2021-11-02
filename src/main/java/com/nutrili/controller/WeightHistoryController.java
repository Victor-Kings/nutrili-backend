package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.database.entity.User;
import com.nutrili.service.WeightHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/WeightHistory")
public class WeightHistoryController {

    @Autowired
    WeightHistoryService weightHistoryService;

    @GetMapping(value= "/getWeightHistory")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> getWeightHistory(@RequestParam @Valid UUID patientID){
        return ResponseEntity.ok( weightHistoryService.getWeightChartData(patientID));
    }

}
