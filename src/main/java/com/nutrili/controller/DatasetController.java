package com.nutrili.controller;

import com.nutrili.Utils.RoleConst;
import com.nutrili.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/dataset")
public class DatasetController {

    @Autowired
    DatasetService datasetService;

    @PostMapping("/updateData")
    @Secured({RoleConst.ROLE_NUTRITIONIST})
    public ResponseEntity updateData (@RequestPart(value = "excel", required = false) MultipartFile excel) throws IOException {
        datasetService.updateDataset(excel);
        return ResponseEntity.ok("ok");
    }
}
