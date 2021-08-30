package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.external.DTO.AnswerDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.User;
import com.nutrili.service.AnswerService;
import com.nutrili.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping("/insertAnswer")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity insertAnswer(@Valid @RequestBody List<AnswerDTO> answerDTOList)
    {
        answerService.insertAnswer((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),answerDTOList);
        return new ResponseEntity("Answers were saved successfuly", HttpStatus.OK);
    }
}
