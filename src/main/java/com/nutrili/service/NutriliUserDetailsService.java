package com.nutrili.service;

import com.nutrili.exception.UserNotFoundException;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NutriliUserDetailsService extends UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.print("\n yyyyyy"+username + "\n");
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

}
