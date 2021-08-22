package com.nutrili.service;

import com.nutrili.exception.RepeatedEmailException;
import com.nutrili.exception.RepeatedPhoneException;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.*;
import com.nutrili.external.database.repository.NutritionistRepository;
import com.nutrili.external.database.repository.PatientRepository;
import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void insertUser(UserDTO userDTO)
    {
        if(userRepository.findByPhone(userDTO.getPhone()).isPresent())
            throw new RepeatedPhoneException();

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new RepeatedEmailException();

        if(userDTO.isNutritionist())
        {
            Nutritionist nutritionist = new Nutritionist();
            DtoToNutritionist(userDTO,nutritionist);
            nutritionistRepository.save(nutritionist);

        } else {
            Patient patient = new Patient();
            DtoToPatient(userDTO,patient);
            patientRepository.save(patient);
        }
    }

    public Boolean getUserByPhone(String phone)
    {
        Optional<User> user =userRepository.findByPhone(phone);
        if(user.isPresent())
        {
            return true;
        }
        return false;
    }

    public void DtoToNutritionist(UserDTO userDTO, Nutritionist nutritionist)
    {
        Role role = new Role("ROLE_NUTRITIONIST");

        nutritionist.setScore(userDTO.getScore());
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

    public void DtoToPatient(UserDTO userDTO, Patient patient)
    {
        Role role = new Role("ROLE_PATIENT");

        patient.setBirth(userDTO.getBirth());
        patient.setCpf(userDTO.getCpf());
        patient.setEmail(userDTO.getEmail());
        patient.setGender(userDTO.getGender());
        patient.setImage(userDTO.getImage());
        patient.setName(userDTO.getName());
        patient.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        patient.setPhone(userDTO.getPhone());
        patient.setWeight(userDTO.getWeight());
        patient.setHeight(userDTO.getHeight());
        patient.setRoles(Arrays.asList(role));

    }

}
