package com.nutrili.service;

import com.nutrili.Utils.GenericMethods;
import com.nutrili.config.Properties;
import com.nutrili.exception.InvalidNutritionistRequest;
import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.DTO.*;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.NutritionistApproval;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.NutritionistApprovalRepository;
import com.nutrili.external.database.repository.NutritionistRepository;
import com.nutrili.external.database.repository.PatientRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    NutriliUserDetailsService nutriliUserDetailsService;

    @Autowired
    WeightHistoryService weightHistoryService;

    @Autowired
    MealService mealService;

    @Autowired
    DietService dietService;


    public List<NutritionistDTO> findNutritionist(String searchParameter, int searchMethod)
    {
        List<NutritionistDTO> nutritionistDTOList = new ArrayList<>();
        (searchMethod==1?nutritionistRepository.findByCity("%"+searchParameter+"%"):nutritionistRepository.findByName("%"+searchParameter+"%")).forEach(nutritionist -> {
            NutritionistDTO nutritionistDTO = new NutritionistDTO();

            if(nutritionist.getOfficeId()!=null)
            {
                nutritionistDTO.setCity(nutritionist.getOfficeId().getCity());
                nutritionistDTO.setState(nutritionist.getOfficeId().getState());
            }

            nutritionistDTO.setName(nutritionist.getName());
            nutritionistDTO.setScore(nutritionist.getScore());
            nutritionistDTO.setId(nutritionist.getId());
            nutritionistDTO.setProfilePicture(nutritionist.getImage());
            nutritionistDTO.setPhone(nutritionist.getPhone());
            nutritionistDTOList.add(nutritionistDTO);
        });
        return nutritionistDTOList;
    }

    public void validateNutritionist(String crn, String name) throws IOException {

        Document doc = Jsoup.connect(properties.getNutritionistUrl())
                .data("nome",name)
                .data("registro", crn)
                .validateTLSCertificates(false)
                .post();

        if(doc.select("tr").isEmpty() || doc.select("tr").size()<2){
          //  throw new InvalidCrnException();
        }
        Element tableRow = doc.select("tr").get(1);
        List<String> tableColumn =tableRow.select("td").stream().map(elem -> elem.toString()).collect(Collectors.toList());

        for(int i=0;i<tableColumn.size();i++){
            tableColumn.set(i,tableColumn.get(i).replace("<td>","").replace("</td>",""));
        }

       // if(!tableColumn.get(0).equals(name.toUpperCase()) || !tableColumn.get(1).equals(crn))
          //  throw new InvalidCrnException();

    }

    public void assignNutritionist(UUID requestId, boolean approval){
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

    public void requestNutritionist(UUID nutritionistId, Patient patient){
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

    public DashboardDataDTO getNutritionistRequest(UUID nutritionistId) {
        DashboardDataDTO dashboardDataDTO = new DashboardDataDTO();
        List<NutritionistRequestDTO> nutritionistRequestDTOList = new ArrayList<>();
        nutritionistApprovalRepository.findRequestBynutritionist(nutritionistId, new Date()).forEach(nutritionistApproval -> {
            NutritionistRequestDTO nutritionistRequestDTO = new NutritionistRequestDTO();
            MeasureDTO measureDTO = new MeasureDTO();
            nutritionistRequestDTO.setAnswerList(answerService.getAnswer(nutritionistApproval.getPatient().getId()));
            nutritionistRequestDTO.setRequestId(nutritionistApproval.getId());
            nutritionistRequestDTO.setCpf(nutritionistApproval.getPatient().getCpf());
            nutritionistRequestDTO.setName(nutritionistApproval.getPatient().getName());
            nutritionistRequestDTO.setAge((int) (TimeUnit.DAYS.convert(new Date().getTime() -nutritionistApproval.getPatient().getBirth().getTime(),TimeUnit.MILLISECONDS)/365));
            nutritionistRequestDTO.setDate(nutritionistApproval.getDateOfRequest().toString());
            if(nutritionistApproval.getPatient().getAddressId()!=null)
                nutritionistRequestDTO.setAddress(GenericMethods.mountAddress(nutritionistApproval.getPatient().getAddressId()));

            measureDTO.setHeight(nutritionistApproval.getPatient().getHeight());
            measureDTO.setWeight(nutritionistApproval.getPatient().getWeight());
            measureDTO.setBmi(nutritionistApproval.getPatient().getWeight()/Math.pow((nutritionistApproval.getPatient().getHeight()/100),2));
            nutritionistRequestDTO.setMeasure(measureDTO);
            nutritionistRequestDTOList.add(nutritionistRequestDTO);

        });
        dashboardDataDTO.setNumberOfPendingRequest(nutritionistRequestDTOList.size());
        dashboardDataDTO.setNumberOfPatient(patientRepository.findPatientByNutritionist(nutritionistId).size());
        dashboardDataDTO.setNutritionistDTOList(nutritionistRequestDTOList);
        return dashboardDataDTO;
    }

    public PagedPatientDTO getClient(int pageNumber, boolean asc, String name, UUID nutritionistId){
        Pageable pageable;

        PagedPatientDTO pagedPatientDTO = new PagedPatientDTO();
        Page<Patient> pagePatient;

        if(asc) {
            pageable = PageRequest.of(pageNumber-1, 8, Sort.by(Sort.Direction.ASC, "name"));
        } else {
            pageable = PageRequest.of(pageNumber-1, 8, Sort.by(Sort.Direction.DESC, "name"));
        }

        if(name != null) {
            pagePatient=patientRepository.patientSearchByName(nutritionistId,pageable,"%"+name+"%");
        } else {
            pagePatient=patientRepository.patientSearch(nutritionistId,pageable);
        }
        pagedPatientDTO.setPatientDTOList(preparePatientList(pagePatient));
        pagedPatientDTO.setFirstPage(pagePatient.isFirst());
        pagedPatientDTO.setLastPage(pagePatient.isLast());
        pagedPatientDTO.setNumberOfPages(pagePatient.getTotalPages());

        return pagedPatientDTO;

    }

    private PatientDTO preparePatient(Patient patient){
        PatientDTO patientDTO= new PatientDTO();

        patientDTO.setPatientID(patient.getId());
        patientDTO.setName(Arrays.stream(patient.getName().split(" ")).map(name->name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase()).collect(Collectors.joining(" ")));
        patientDTO.setStatus(patient.getStatus());
        patientDTO.setAge((int) (TimeUnit.DAYS.convert(new Date().getTime() -patient.getBirth().getTime(),TimeUnit.MILLISECONDS)/365));

        if(patient.getDateOfLastMeeting()!=null){
            int days= ((int) (TimeUnit.DAYS.convert(new Date().getTime() -patient.getDateOfLastMeeting().getTime(),TimeUnit.MILLISECONDS)));
            patientDTO.setDateOfLastMeeting(String.valueOf(days));
        }

        patientDTO.setProfileIcon(patient.getImage());

        return patientDTO;

    }

    private List<PatientDTO> preparePatientList(Page<Patient> patients){
        List<PatientDTO> patientList = new ArrayList<>();
        patients.forEach(patient -> {
            patientList.add(preparePatient(patient));
        });
        return patientList;
    }

    public void updatePatient(UserDTO userDTO){
        if(userDTO.getPatientID()!=null){
            nutriliUserDetailsService.getUser(userDTO.getPatientID()).ifPresentOrElse(user->{
                nutriliUserDetailsService.updateUser(user,userDTO);
                    },()->{throw new UserNotFoundException();});
        } else {
            throw new UserNotFoundException();
        }
    }

    private MeasureDTO getPatientMeasure(UUID patientID){
        MeasureDTO measureDTO = new MeasureDTO();
        nutriliUserDetailsService.getUser(patientID).ifPresentOrElse(user->{
            Patient patient = (Patient) user;
            measureDTO.setHeight(patient.getHeight());
            measureDTO.setWeight(patient.getWeight());
            measureDTO.setBmi(patient.getWeight()/Math.pow((patient.getHeight()/100),2));
        },()->{throw new UserNotFoundException();});
        return measureDTO;
    }

    public  PatientDashboardDTO getPatientDashboard(UUID patientID){
        PatientDashboardDTO patientDashboardDTO = new PatientDashboardDTO();
        patientDashboardDTO.setMeasure(getPatientMeasure(patientID));
        patientDashboardDTO.setWeightHistoryChart(weightHistoryService.getWeightChartData(patientID));
        patientDashboardDTO.setPatient(preparePatient(patientRepository.findById(patientID).get()));
        patientDashboardDTO.setMealChartDataDTO(mealService.getChart(patientID));
        patientDashboardDTO.setDiet(dietService.getDietList(patientID));
        return patientDashboardDTO;
    }

    public NutritionistInfoDTO getNutritionistInfo(Nutritionist nutritionist){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        NutritionistInfoDTO nutritionistInfoDTO = new NutritionistInfoDTO();
        nutritionistInfoDTO.setPhone(nutritionist.getPhone());
        nutritionistInfoDTO.setOffice(nutritionist.getOfficeId());
        nutritionistInfoDTO.setCrnType(nutritionist.getCrnType());
        nutritionistInfoDTO.setScore(nutritionist.getScore());
        nutritionistInfoDTO.setProfilePic(nutritionist.getImage());
        nutritionistInfoDTO.setNumberOfPatients(patientRepository.findPatientByNutritionist(nutritionist.getId()).size());
        nutritionistInfoDTO.setName(Arrays.stream(nutritionist.getName().split(" ")).map(name->name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase()).collect(Collectors.joining(" ")));
        nutritionistInfoDTO.setBirth(formatter.format(nutritionist.getBirth()));
        return nutritionistInfoDTO;

    }

}
