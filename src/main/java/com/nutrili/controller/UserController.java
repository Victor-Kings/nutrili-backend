package com.nutrili.controller;


import com.nutrili.misc.RoleConst;
import com.nutrili.external.database.entity.User;
import com.nutrili.external.database.repository.RoleRepository;
import com.nutrili.external.database.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    Logger logger;

    @Autowired
    DefaultTokenServices defaultTokenServices;

    @Secured({RoleConst.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user){
        user = this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @Secured({RoleConst.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> edit(@RequestBody User user){
        user = this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @Secured({RoleConst.ROLE_CLIENT, RoleConst.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> list(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return new ResponseEntity<Page<User>>(userRepository.findAll(pageable), HttpStatus.OK);
    }

    @Secured({RoleConst.ROLE_CLIENT, RoleConst.ROLE_ADMIN})
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(){
       User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(
                "teste", user.getUsername());
        for (OAuth2AccessToken token : tokens) {
            defaultTokenServices.revokeToken(token.getValue().toString());
        }
        return new ResponseEntity<String>("off", HttpStatus.OK);
    }


    @Secured({RoleConst.ROLE_ADMIN})
    @RequestMapping(value = "/vruum", method = RequestMethod.GET)
    public ResponseEntity<?> vrum()
    {
        return new ResponseEntity<String>("vrrum",HttpStatus.OK);
    }

}
