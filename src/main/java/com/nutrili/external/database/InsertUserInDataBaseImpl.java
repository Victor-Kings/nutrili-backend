package com.nutrili.external.database;

import com.nutrili.external.InsertUserInDataBase;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertUserInDataBaseImpl implements InsertUserInDataBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User execute(User user) {
        User response = userRepository.findByEmail(user.getEmail());
        return  response;
    }
}
