package com.nutrili.controller;


import com.nutrili.external.DTO.UserDTO;
import com.nutrili.service.NutriliUserDetailsService;
import com.nutrili.service.SmsService;
import com.nutrili.service.ValidateTokenService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    Logger logger;

    @Autowired
    ValidateTokenService validateTokenService;

    @Autowired
    SmsService smsService;

    @Autowired
    NutriliUserDetailsService userDetailsService;



    @PostMapping(value = "/insertUser")
    public ResponseEntity<?> insertUser(@RequestHeader(value="AOBARIZATION",required = true) String authorization, @Valid UserDTO userDTO)
    {
        validateTokenService.validateToken(authorization);
        userDetailsService.insertUser(userDTO);
        return new ResponseEntity<String>("User was created successfully",HttpStatus.OK);

    }

    @PostMapping(value="/smsToken")
    public ResponseEntity<?> smsToken(@RequestHeader(value="AOBARIZATION",required = true) String authorization, @RequestParam @Valid String phone)
    {
        validateTokenService.validateToken(authorization);
        smsService.generateSmsToken(phone);
        return new ResponseEntity<String>("SMS was sent successfully",HttpStatus.OK);

    }
/*
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
    }*/

}
