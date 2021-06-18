package com.nutrili.config;


import com.nutrili.misc.RoleConst;
import com.nutrili.entity.Role;
import com.nutrili.entity.User;
import com.nutrili.repository.RoleRepository;
import com.nutrili.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            createUser("Admin", "admin", passwordEncoder.encode("123456"), RoleConst.ROLE_ADMIN);
            createUser("testes", "cliente", passwordEncoder.encode("123456"), RoleConst.ROLE_CLIENT);
        }

    }

    public void createUser(String name, String email, String password, String roleName) {

        Role role = new Role(roleName);
        this.roleRepository.save(role);
        User user = new User(name, email, password, Arrays.asList(role));
        userRepository.save(user);
    }

}

