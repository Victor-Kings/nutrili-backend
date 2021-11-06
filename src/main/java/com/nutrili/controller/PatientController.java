package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping("/getPatient")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity getNutritionist() {
        return ResponseEntity.ok(patientService.getProfile((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

}
