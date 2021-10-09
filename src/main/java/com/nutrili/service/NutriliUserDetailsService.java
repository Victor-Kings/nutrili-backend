package com.nutrili.service;

import com.nutrili.Utils.GenericMethods;
import com.nutrili.config.Properties;
import com.nutrili.exception.RepeatedEmailException;
import com.nutrili.exception.RepeatedPhoneException;
import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.DTO.NewUserDTO;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public void insertUser(UserDTO userDTO) {
        if (userRepository.findByPhone(userDTO.getPhone()).isPresent())
            throw new RepeatedPhoneException();

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new RepeatedEmailException();

        Nutritionist nutritionist = new Nutritionist();
        DtoToNutritionist(userDTO, nutritionist);
        Address address = new Address();
        address.setNumber(userDTO.getNumber());
        address.setNeighborhood(userDTO.getNeighborhood());
        address.setStreet(userDTO.getStreet());
        address.setCity(userDTO.getCity());
        address.setCep(userDTO.getCep());
        address.setState(userDTO.getState());
        nutritionist.setAddressId(address);

        nutritionistRepository.save(nutritionist);
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

        user.setBirth(GenericMethods.nvl(userDTO.getBirth(),user.getBirth()));

        user.setEmail(GenericMethods.nvl(userDTO.getEmail(),user.getEmail()));

        user.setCpf(GenericMethods.nvl(userDTO.getCpf(),user.getCpf()));

        user.setName(GenericMethods.nvl(userDTO.getName(),user.getName()));

        user.setGender(GenericMethods.nvl(userDTO.getGender(),user.getGender()));

        user.setBirth(GenericMethods.nvl(userDTO.getBirth(),user.getBirth()));

        user.setImage(GenericMethods.nvl(userDTO.getImage(),user.getImage()));

        if (user.getAddressId() != null) {

            user.getAddressId().setCep(GenericMethods.nvl(userDTO.getCep(),user.getAddressId().getCep()));

            user.getAddressId().setState(GenericMethods.nvl(userDTO.getState(),user.getAddressId().getState()));

            user.getAddressId().setCity(GenericMethods.nvl(userDTO.getCity(),user.getAddressId().getCity()));

            user.getAddressId().setNeighborhood(GenericMethods.nvl(userDTO.getNeighborhood(),user.getAddressId().getNeighborhood()));

            user.getAddressId().setStreet(GenericMethods.nvl(userDTO.getStreet(),user.getAddressId().getStreet()));

            user.getAddressId().setNumber(GenericMethods.nvl(userDTO.getNumber(),user.getAddressId().getNumber()));

        } else {
            Address address = new Address();

            address.setCep(GenericMethods.nvl(userDTO.getCep(),null));

            address.setState(GenericMethods.nvl(userDTO.getState(),null));

            address.setCity(GenericMethods.nvl(userDTO.getCity(),null));

            address.setNeighborhood(GenericMethods.nvl(userDTO.getNeighborhood(),null));

            address.setStreet(GenericMethods.nvl(userDTO.getStreet(),null));

            address.setNumber(GenericMethods.nvl(userDTO.getNumber(),null));

            user.setAddressId(address);
        }

        if (userDTO.isNutritionist()) {
            Nutritionist nutritionist = (Nutritionist) user;

            nutritionist.setCrn(GenericMethods.nvl(userDTO.getCrn(),nutritionist.getCrn()));

            nutritionist.setCrnType(GenericMethods.nvl(userDTO.getCrnType(),nutritionist.getCrnType()));

            nutritionistRepository.save(nutritionist);


        } else {
            Patient patient = (Patient) user;

            patient.setHeight(GenericMethods.nvl(userDTO.getHeight(),patient.getHeight()));

            patient.setWeight(GenericMethods.nvl(userDTO.getWeight(),patient.getWeight()));

            patientRepository.save(patient);
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
        nutritionist.setCpf(userDTO.getCpf());
        nutritionist.setEmail(userDTO.getEmail());
        nutritionist.setGender(userDTO.getGender());
        nutritionist.setImage(userDTO.getImage());
        nutritionist.setName(userDTO.getName());
        nutritionist.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        nutritionist.setPhone(userDTO.getPhone());
        nutritionist.setRoles(Arrays.asList(role));
    }

}
