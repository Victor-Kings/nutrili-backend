package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.service.NutritionistService;
import com.nutrili.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/nutritionist")
public class NutritionistController {

    @Autowired
    NutritionistService nutritionistService;

    @Autowired
    ValidateTokenService validateTokenService;

    @GetMapping("/getNutritionist")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity getNutritionist(@NotNull @RequestParam String searchParameter, @NotNull @RequestParam int searchMethod) {
        return ResponseEntity.ok(nutritionistService.findNutritionist(searchParameter,searchMethod));
    }

    @PostMapping("/assignNutritionist")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity assignNutritionist(@NotNull @RequestParam UUID requestId,@NotNull @RequestParam boolean approval){
        nutritionistService.assignNutritionist(requestId,approval);
        return ResponseEntity.ok("nutritionist answer was recorded succesfully");
    }

    @PostMapping("/requestNutritionist")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity requestNutritionist(@NotNull @RequestParam UUID nutritionistId){
        nutritionistService.requestNutritionist(nutritionistId,(Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok("selected nutritionist received a request successfully");
    }

    @GetMapping("/getNutritionistRequest")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity getNutritionistRequest(){
        return ResponseEntity.ok(nutritionistService.getNutritionistRequest(((Nutritionist) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

    @GetMapping(value="/getClient")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> getClient(@NotNull @RequestParam int pageNumber, @NotNull @RequestParam boolean asc, @RequestParam String name){
        return ResponseEntity.ok(nutritionistService.getClient(pageNumber,asc,name,((Nutritionist) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

    @PutMapping("/updatePatient")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> updatePatient( @Valid @RequestBody UserDTO userDTO)
    {
        nutritionistService.updatePatient(userDTO);
        return new ResponseEntity<String>("User was modified successfully", HttpStatus.OK);
    }

    @GetMapping("/getPatientDashboard")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> getPatientDashboard(@NotNull @RequestParam UUID patientID) {
        return ResponseEntity.ok(nutritionistService.getPatientDashboard(patientID));
    }

    @GetMapping("/getNutritionistInfo")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> getNutritionistInfo() {
        return ResponseEntity.ok(nutritionistService.getNutritionistInfo(((Nutritionist) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }



}
