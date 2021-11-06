package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.DietDTO;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/diet")
public class DietController {
    @Autowired
    DietService dietService;

    @PutMapping("/updateDiet")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity<?> updatePatient(@NotNull @RequestParam UUID patientID, @Valid @RequestBody List<DietDTO> dietDTOList)
    {
        dietService.updateDiet(patientID,dietDTOList);
        return new ResponseEntity<String>("diet was modified successfully", HttpStatus.OK);
    }

    @GetMapping("/getDiet")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity<?> getDiet()
    {
        return ResponseEntity.ok(dietService.getDietList(((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
    }

}
