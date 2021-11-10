package com.nutrili.service;

import com.nutrili.Utils.GenericMethods;
import com.nutrili.config.Properties;
import com.nutrili.exception.*;
import com.nutrili.external.DTO.NewUserDTO;
import com.nutrili.external.DTO.UserBasicInfoDTO;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.*;
import com.nutrili.external.database.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NutriliUserDetailsService implements UserDetailsService {

    @Autowired
    SmsService smsService;

    @Autowired
    Properties properties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NutritionistApprovalRepository nutritionistApprovalRepository;

    @Autowired
    private NutritionistService nutritionistService;

    @Autowired
    private WeightHistoryService weightHistoryService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username==null || username.equals(""))
            throw new RuntimeException("Unhandled Exception");

        List<String> userData = Stream.of(StringUtils.split(
                username, ":")).collect(Collectors.toList());

        User user = null;
        if(userData.size()>1) {
            if (Integer.parseInt(userData.get(1)) == 1) {
                Optional<User> userValidation = userRepository.findByEmail(userData.get(0));
                if (userValidation.isPresent())
                    user = userValidation.get();
            } else {
                Optional<SmsToken> smsToken = smsService.findPhoneCode(userData.get(0), userData.get(2));
                if (smsToken.isPresent()) {
                    Optional<User> userValidation = userRepository.findByPhone(userData.get(0));
                    if (userValidation.isPresent()) {
                        user = userValidation.get();
                        user.setPassword(passwordEncoder.encode(properties.getTwilioPassword()));
                    }
                    smsService.deletePhoneByCode(userData.get(0));
                }
            }
        } else {
                Optional<User>userValidation = userRepository.findByEmail(userData.get(0));
                if (userValidation.isPresent())
                    user = userValidation.get();
                else {
                    userValidation = userRepository.findByPhone(userData.get(0));
                    if (userValidation.isPresent())
                        user = userValidation.get();
                }
        }

        if(user==null)
        {
            throw new UserNotFoundException();
        }

        return user;

    }

    private void validateUser(String phone, String email,String cpf,String userPhone){

        if (phone!=null && userRepository.findByPhone(phone).isPresent() && !userPhone.equals(phone))
            throw new RepeatedPhoneException();

        if (email!=null && userRepository.findByEmail(email).isPresent())
            throw new RepeatedEmailException();

        if(cpf!=null && userRepository.findByCpf(cpf).isPresent())
            throw new InvalidCpfException();

    }

    public void insertUser(UserDTO userDTO) {

        validateUser(userDTO.getPhone(),userDTO.getEmail(),userDTO.getCpf().replace(".","").replace("/",""),null);
        /*
        try {
            nutritionistService.validateNutritionist(userDTO.getCrn(), userDTO.getName());
        } catch(Exception e) {
            System.out.println(e);
            throw new SomethingWentWrongException();
        }*/

        Nutritionist nutritionist = new Nutritionist();
        DtoToNutritionist(userDTO, nutritionist);
        nutritionist.setAddressId(userDTO.getPersonalAddress());

        nutritionistRepository.save(nutritionist);
    }

    public UserBasicInfoDTO getUser(User user) {
        UserBasicInfoDTO userBasicInfoDTO = new UserBasicInfoDTO();
        userBasicInfoDTO.setEmail(user.getEmail());
        userBasicInfoDTO.setName(user.getName());
        userBasicInfoDTO.setProfilePicture(user.getImage());
        return userBasicInfoDTO;
    }

    public void insertUserByPhone(String phone) {
        Patient patient = new Patient();
        patient.setPhone(phone);
        patient.setEmail(phone);
        Role role = new Role("ROLE_PATIENT");
        patient.setRoles(Arrays.asList(role));
        patientRepository.save(patient);
    }

    public void updateUser(User user, UserDTO userDTO) {

        if(userDTO.getCpf()!=null){
            userDTO.setCpf(userDTO.getCpf().replace(".","").replace("/",""));
        }

        validateUser(userDTO.getPhone(),userDTO.getEmail(),userDTO.getCpf(),user.getPhone());

        user.setBirth(GenericMethods.nvl(userDTO.getBirth(),user.getBirth()));

        user.setEmail(GenericMethods.nvl(userDTO.getEmail(),user.getEmail()));

        user.setCpf(GenericMethods.nvl(userDTO.getCpf(),user.getCpf()));

        user.setName(GenericMethods.nvl(userDTO.getName(),user.getName()));

        user.setGender(GenericMethods.nvl(userDTO.getGender(),user.getGender()));

        user.setBirth(GenericMethods.nvl(userDTO.getBirth(),user.getBirth()));

        user.setImage(GenericMethods.nvl(userDTO.getImage(),user.getImage()));

        if (user.getAddressId() != null && userDTO.getPersonalAddress()!=null) {

            user.getAddressId().setCep(GenericMethods.nvl(userDTO.getPersonalAddress().getCep(),user.getAddressId().getCep()));

            user.getAddressId().setState(GenericMethods.nvl(userDTO.getPersonalAddress().getState(),user.getAddressId().getState()));

            user.getAddressId().setCity(GenericMethods.nvl(userDTO.getPersonalAddress().getCity(),user.getAddressId().getCity()));

            user.getAddressId().setNeighborhood(GenericMethods.nvl(userDTO.getPersonalAddress().getNeighborhood(),user.getAddressId().getNeighborhood()));

            user.getAddressId().setStreet(GenericMethods.nvl(userDTO.getPersonalAddress().getStreet(),user.getAddressId().getStreet()));

            user.getAddressId().setNumber(GenericMethods.nvl(userDTO.getPersonalAddress().getNumber(),user.getAddressId().getNumber()));

        } else if(userDTO.getPersonalAddress()!=null) {
            user.setAddressId(userDTO.getPersonalAddress());
        }

        if (userDTO.isNutritionist()) {
            Nutritionist nutritionist = (Nutritionist) user;

            nutritionist.setCrn(GenericMethods.nvl(userDTO.getCrn(),nutritionist.getCrn()));

            nutritionist.setCrnType(GenericMethods.nvl(userDTO.getCrnType(),nutritionist.getCrnType()));

            if (nutritionist.getOfficeId() != null && userDTO.getOfficeAddress()!=null) {

                nutritionist.getOfficeId().setCep(GenericMethods.nvl(userDTO.getOfficeAddress().getCep(),user.getAddressId().getCep()));

                nutritionist.getOfficeId().setState(GenericMethods.nvl(userDTO.getOfficeAddress().getState(),user.getAddressId().getState()));

                nutritionist.getOfficeId().setCity(GenericMethods.nvl(userDTO.getOfficeAddress().getCity(),user.getAddressId().getCity()));

                nutritionist.getOfficeId().setNeighborhood(GenericMethods.nvl(userDTO.getOfficeAddress().getNeighborhood(),user.getAddressId().getNeighborhood()));

                nutritionist.getOfficeId().setStreet(GenericMethods.nvl(userDTO.getOfficeAddress().getStreet(),user.getAddressId().getStreet()));

                nutritionist.getOfficeId().setNumber(GenericMethods.nvl(userDTO.getOfficeAddress().getNumber(),user.getAddressId().getNumber()));

                nutritionist.getOfficeId().setOfficeName(GenericMethods.nvl(userDTO.getOfficeAddress().getOfficeName(),user.getAddressId().getOfficeName()));

                nutritionist.getOfficeId().setOfficePhone(GenericMethods.nvl(userDTO.getOfficeAddress().getOfficePhone(),user.getAddressId().getOfficePhone()));

            } else if(userDTO.getOfficeAddress()!=null) {
                nutritionist.setOfficeId(userDTO.getOfficeAddress());
            }

            nutritionistRepository.save(nutritionist);


        } else {
            Patient patient = (Patient) user;

            patient.setHeight(GenericMethods.nvl(userDTO.getHeight(),patient.getHeight()));

            patient.setWeight(GenericMethods.nvl(userDTO.getWeight(),patient.getWeight()));

            patientRepository.save(patient);

            if(userDTO.getWeight()!=null) {
                weightHistoryService.insertWeightHistory(userDTO.getWeight(),patient);
            }
        }
    }

    public Boolean getUserByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public NewUserDTO isNewUser(Patient patient) {
        NewUserDTO newUserDTO = new NewUserDTO();
        if (patient.getName() != null)
            newUserDTO.setNewUser(false);

        Optional<List<QuestionAnswer>> questionAnswer = answerRepository.findQuestion(patient.getId());

        if(questionAnswer.isPresent())
            newUserDTO.setAncientPlusComplete(true);

        if(patientRepository.findPatientWithNutritionist(patient.getId()).isPresent() || !nutritionistApprovalRepository.findRecentRequest(patient.getId(), new Date()).isEmpty())
            newUserDTO.setAbleToSearchNutritionist(false);
        return newUserDTO;
    }

    private void DtoToNutritionist(UserDTO userDTO, Nutritionist nutritionist) {
        Role role = new Role("ROLE_NUTRITIONIST");

        nutritionist.setScore(10);
        nutritionist.setCrn(userDTO.getCrn());
        nutritionist.setCrnType(userDTO.getCrnType());
        nutritionist.setBirth(userDTO.getBirth());
        nutritionist.setCpf(userDTO.getCpf().replace(".","").replace("/",""));
        nutritionist.setEmail(userDTO.getEmail());
        nutritionist.setGender(userDTO.getGender());
        nutritionist.setImage(userDTO.getImage());
        nutritionist.setName(userDTO.getName());
        nutritionist.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        nutritionist.setPhone(userDTO.getPhone());
        nutritionist.setRoles(Arrays.asList(role));
    }

    public Optional<User> getUser(UUID userId){
        return userRepository.findById(userId);
    }

    public void setImage(String image,User user){
        user.setImage(image);
        userRepository.save(user);
    }

}
