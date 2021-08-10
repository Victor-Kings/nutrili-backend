package com.nutrili.service;

import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    protected UserRepository userRepository;

    public void insertUser()
    {

    }

}
