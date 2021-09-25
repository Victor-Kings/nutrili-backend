package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.User;
import com.nutrili.service.NutritionistService;
import com.nutrili.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

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
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity assignNutritionist(@NotNull @RequestParam long nutritionistId){
        nutritionistService.assignNutritionist(nutritionistId,(Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok("selected nutritionist was assigned to the patient successfully");
    }

    @PostMapping("/requestNutritionist")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity requestNutritionist(@NotNull @RequestParam long nutritionistId){
        nutritionistService.requestNutritionist(nutritionistId,(Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok("selected nutritionist received a request successfully");
    }

    @GetMapping(value="/validateCrn")
    public ResponseEntity<?> validateCrn(@RequestHeader(value="AOBARIZATION",required = true) String authorization,@NotNull @RequestParam String crn, @NotNull @RequestParam String nome) throws IOException {
        validateTokenService.validateToken(authorization);
        return ResponseEntity.ok(nutritionistService.validateNutritionist(crn,nome));
    }

}
