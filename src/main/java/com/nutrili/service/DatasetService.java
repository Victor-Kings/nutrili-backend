package com.nutrili.service;

import com.nutrili.Utils.ExcelHelper;
import com.nutrili.external.database.entity.Dataset;
import com.nutrili.external.database.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    public void updateDataset(MultipartFile multipartFile) throws IOException {
        List<Dataset> foods = ExcelHelper.excelToList(multipartFile.getInputStream());
        datasetRepository.saveAll(foods);
    }

}
