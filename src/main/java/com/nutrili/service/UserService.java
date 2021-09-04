package com.nutrili.service;

import com.nutrili.exception.RepeatedEmailException;
import com.nutrili.exception.RepeatedPhoneException;
import com.nutrili.external.DTO.NewUserDTO;
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

    public void insertUser(UserDTO userDTO) {
        if (userRepository.findByPhone(userDTO.getPhone()).isPresent())
            throw new RepeatedPhoneException();

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new RepeatedEmailException();

        Nutritionist nutritionist = new Nutritionist();
        DtoToNutritionist(userDTO, nutritionist);
        nutritionistRepository.save(nutritionist);
    }

    public void insertUserByPhone(String phone) {
        Patient patient = new Patient();
        patient.setPhone(phone);
        Role role = new Role("ROLE_PATIENT");
        patient.setRoles(Arrays.asList(role));
        patientRepository.save(patient);
    }

    public void updateUser(User user, UserDTO userDTO) {
        if (userDTO.getBirth() != null)
            user.setBirth(userDTO.getBirth());

        if (userDTO.getEmail() != null)
            user.setEmail(userDTO.getEmail());

        if (userDTO.getCpf() != null)
            user.setCpf(userDTO.getCpf());

        if (userDTO.getName() != null)
            user.setName(userDTO.getName());

        if (userDTO.getGender() != null)
            user.setGender(userDTO.getGender());

        if (userDTO.getBirth() != null)
            user.setBirth(userDTO.getBirth());

        if (userDTO.getImage() != null)
            user.setImage(userDTO.getImage());

        if (user.getAddressId() != null) {
            if (userDTO.getCep() != null)
                user.getAddressId().setCep(userDTO.getCep());

            if (userDTO.getState() != null)
                user.getAddressId().setState(userDTO.getState());

            if (userDTO.getCity() != null)
                user.getAddressId().setCity(userDTO.getCity());

            if (userDTO.getNeighborhood() != null)
                user.getAddressId().setNeighborhood(userDTO.getNeighborhood());

            if (userDTO.getStreet() != null)
                user.getAddressId().setStreet(userDTO.getStreet());

            if (userDTO.getNumber() != null)
                user.getAddressId().setNumber(userDTO.getNumber());
        } else {
            Address address = new Address();

            if (userDTO.getCep() != null)
                address.setCep(userDTO.getCep());

            if (userDTO.getState() != null)
                address.setState(userDTO.getState());

            if (userDTO.getCity() != null)
                address.setCity(userDTO.getCity());

            if (userDTO.getNeighborhood() != null)
                address.setNeighborhood(userDTO.getNeighborhood());

            if (userDTO.getStreet() != null)
                address.setStreet(userDTO.getStreet());

            if (userDTO.getNumber() != null)
                address.setNumber(userDTO.getNumber());

            user.setAddressId(address);
        }

        if (userDTO.isNutritionist()) {
            Nutritionist nutritionist = (Nutritionist) user;

            if (userDTO.getCrn() != null)
                nutritionist.setCrn(userDTO.getCrn());

            if (userDTO.getScore() != null)
                nutritionist.setScore(userDTO.getScore());

            if (userDTO.getCrnType() != null)
                nutritionist.setCrnType(userDTO.getCrnType());

            nutritionistRepository.save(nutritionist);


        } else {
            Patient patient = (Patient) user;

            if (userDTO.getHeight() != null)
                patient.setHeight(userDTO.getHeight());

            if (userDTO.getWeight() != null)
                patient.setWeight(userDTO.getWeight());

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
        if (patient.getName() != null) {
            newUserDTO.setNewUser(false);
        }
        return newUserDTO;
    }

    private void DtoToNutritionist(UserDTO userDTO, Nutritionist nutritionist) {
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

}
