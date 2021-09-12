package com.nutrili.service;

import com.nutrili.Utils.GenericMethods;
import com.nutrili.exception.RepeatedEmailException;
import com.nutrili.exception.RepeatedPhoneException;
import com.nutrili.external.DTO.NewUserDTO;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.*;
import com.nutrili.external.database.repository.NutritionistRepository;
import com.nutrili.external.database.repository.PatientRepository;
import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

            nutritionist.setScore(GenericMethods.nvl(userDTO.getScore(),nutritionist.getScore()));

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

    public NewUserDTO  isNewUser(Patient patient) {
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
