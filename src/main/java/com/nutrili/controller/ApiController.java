package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @PostMapping("/getLabelDetection")
    @Secured({RoleConst.ROLE_NUTRITIONIST,RoleConst.ROLE_PATIENT})
    public List<String> getLabelDetection(@RequestPart(value = "pic", required = false) MultipartFile pic) throws IOException {
        return apiService.getLabel(pic);
    }


}
