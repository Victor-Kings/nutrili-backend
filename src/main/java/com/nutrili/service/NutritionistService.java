package com.nutrili.service;

import com.nutrili.Utils.GenericMethods;
import com.nutrili.config.Properties;
import com.nutrili.exception.InvalidNutritionistRequest;
import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.DTO.MeasureDTO;
import com.nutrili.external.DTO.NutritionistDTO;
import com.nutrili.external.DTO.NutritionistRequestDTO;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Autowired
    Properties properties;

    @Autowired
    NutritionistApprovalRepository nutritionistApprovalRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AnswerService answerService;


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

    public void assignNutritionist(long requestId, boolean approval){
       Optional<NutritionistApproval> nutritionistApprovalValidation= nutritionistApprovalRepository.findById(requestId);
       if(nutritionistApprovalValidation.isPresent()){
           NutritionistApproval nutritionistApproval = nutritionistApprovalValidation.get();
           nutritionistApproval.setApproval(approval);
           if(approval){
               nutritionistApproval.getPatient().setNutritionist(nutritionistApproval.getNutritionist());
               notificationService.createNotification(nutritionistApproval.getPatient(),"Sistema","nutricionista aprovou você como paciente");
           } else {
               notificationService.createNotification(nutritionistApproval.getPatient(),"Sistema","nutricionista não aprovou você como paciente");

           }
            nutritionistApprovalRepository.save(nutritionistApproval);
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
                notificationService.createNotification(nutritionistValidation.get(),"Sistema","Você possui uma nova requisição de paciente");
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new InvalidNutritionistRequest();
        }
    }

    public List<NutritionistRequestDTO> getNutritionistRequest(long nutritionistId) {
        List<NutritionistRequestDTO> nutritionistRequestDTOList = new ArrayList<>();
        nutritionistApprovalRepository.findRequestBynutritionist(nutritionistId, new Date()).forEach(nutritionistApproval -> {
            NutritionistRequestDTO nutritionistRequestDTO = new NutritionistRequestDTO();
            MeasureDTO measureDTO = new MeasureDTO();
            nutritionistRequestDTO.setAnswerList(answerService.getAnswer(nutritionistApproval.getPatient().getId()));
            nutritionistRequestDTO.setRequestId(nutritionistApproval.getId());
            nutritionistRequestDTO.setCpf(nutritionistApproval.getPatient().getCpf());
            nutritionistRequestDTO.setAge((int) (TimeUnit.DAYS.convert(new Date().getTime() -nutritionistApproval.getPatient().getBirth().getTime(),TimeUnit.MILLISECONDS)/365));
            nutritionistRequestDTO.setDate(nutritionistApproval.getDateOfRequest());
            if(nutritionistApproval.getPatient().getAddressId()!=null)
                nutritionistRequestDTO.setAddress(GenericMethods.mountAddress(nutritionistApproval.getPatient().getAddressId()));

            measureDTO.setHeight(nutritionistApproval.getPatient().getHeight());
            measureDTO.setWeight(nutritionistApproval.getPatient().getWeight());
            measureDTO.setBmi(nutritionistApproval.getPatient().getWeight()/Math.pow((nutritionistApproval.getPatient().getHeight()/100),2));
            nutritionistRequestDTO.setMeasure(measureDTO);
            nutritionistRequestDTOList.add(nutritionistRequestDTO);

        });
        return nutritionistRequestDTOList;
    }


}
