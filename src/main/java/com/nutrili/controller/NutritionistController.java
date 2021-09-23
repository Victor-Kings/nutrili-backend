package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.service.NutritionistService;
import com.nutrili.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    public ResponseEntity getByCity(@NotNull @RequestParam String searchParameter, @NotNull @RequestParam int searchMethod) {
        return ResponseEntity.ok(nutritionistService.findNutritionist(searchParameter,searchMethod));
    }

    @GetMapping(value="/validateCrn")
    public ResponseEntity<?> validateCrn(@RequestHeader(value="AOBARIZATION",required = true) String authorization,@NotNull @RequestParam String crn, @NotNull @RequestParam String nome) throws IOException {
        validateTokenService.validateToken(authorization);
        return ResponseEntity.ok(nutritionistService.validateNutritionist(crn,nome));
    }

}
