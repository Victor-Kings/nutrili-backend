package com.nutrili.service;

import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.Nutritionist;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;

    public void insertUser(UserDTO userDTO)
    {
        if(userDTO.isNutritionist())
        {
            Nutritionist nutritionist = new Nutritionist();

        } else {
            Patient patient = new Patient();
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

    }

    public void DtoToPatient(UserDTO userDTO, Patient patient)
    {
        
    }

}
