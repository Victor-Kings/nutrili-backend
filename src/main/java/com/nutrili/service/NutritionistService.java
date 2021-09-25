package com.nutrili.service;

import com.nutrili.config.Properties;
import com.nutrili.exception.InvalidNutritionistRequest;
import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.DTO.NutritionistDTO;
import com.nutrili.external.DTO.ValidNutritionistDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.NutritionistApproval;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.repository.NutritionistApprovalRepository;
import com.nutrili.external.database.repository.NutritionistRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Autowired
    Properties properties;

    @Autowired
    NutritionistApprovalRepository nutritionistApprovalRepository;

    public List<NutritionistDTO> findNutritionist(String searchParameter, int searchMethod)
    {
        List<NutritionistDTO> nutritionistDTOList = new ArrayList<>();
        (searchMethod==1?nutritionistRepository.findByCity("%"+searchParameter+"%"):nutritionistRepository.findByName("%"+searchParameter+"%")).forEach(nutritionist -> {
            NutritionistDTO nutritionistDTO = new NutritionistDTO();
            nutritionistDTO.setCity(nutritionist.getAddressId().getCity());
            nutritionistDTO.setName(nutritionist.getName());
            nutritionistDTO.setState(nutritionist.getAddressId().getState());
            nutritionistDTO.setScore(nutritionist.getScore());
            nutritionistDTO.setId(nutritionist.getId());
            nutritionistDTO.setProfilePicture(nutritionist.getImage());
            nutritionistDTO.setPhone(nutritionist.getPhone());
            nutritionistDTOList.add(nutritionistDTO);
        });
        return nutritionistDTOList;
    }

    public ValidNutritionistDTO validateNutritionist(String crn, String name) throws IOException {

        Document doc = Jsoup.connect(properties.getNutritionistUrl())
                .data("nome",name)
                .data("registro", crn)
                .validateTLSCertificates(false)
                .post();

        Element tableRow = doc.select("tr").get(1);
        List<String> tableColumn =tableRow.select("td").stream().map(elem -> elem.toString()).collect(Collectors.toList());

        for(int i=0;i<tableColumn.size();i++){
            tableColumn.set(i,tableColumn.get(i).replace("<td>","").replace("</td>",""));
        }

        ValidNutritionistDTO validNutritionistDTO = new ValidNutritionistDTO();

        if(!tableColumn.get(0).equals(name.toUpperCase()) || !tableColumn.get(1).equals(crn))
            validNutritionistDTO.setNutritionist(false);

        return validNutritionistDTO;
    }

    public void assignNutritionist(long nutritionistId, Patient patient){
       Optional<Nutritionist> nutritionistValidation= nutritionistRepository.findById(nutritionistId);
       if(nutritionistValidation.isPresent()){
           Nutritionist nutritionist = nutritionistValidation.get();
           nutritionist.getPatientList().add(patient);
           patient.setNutritionist(nutritionist);
           nutritionistRepository.save(nutritionist);
       } else {
           throw new UserNotFoundException();
       }
    }

    public void requestNutritionist(long nutritionistId, Patient patient){
        if(nutritionistApprovalRepository.findRecentRequest(patient.getId(),new Date()).isEmpty()) {
            Optional<Nutritionist> nutritionistValidation= nutritionistRepository.findById(nutritionistId);
            if (nutritionistValidation.isPresent()) {
                Nutritionist nutritionist = nutritionistValidation.get();
                NutritionistApproval nutritionistApproval = new NutritionistApproval();
                nutritionistApproval.setNutritionist(nutritionist);
                nutritionistApproval.setDateOfRequest(new Date());
                nutritionistApproval.setPatient(patient);
                nutritionistApprovalRepository.save(nutritionistApproval);
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new InvalidNutritionistRequest();
        }
    }

}
