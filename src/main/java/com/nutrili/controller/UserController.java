package com.nutrili.controller;


import com.nutrili.external.DTO.NewUserDTO;
import com.nutrili.external.DTO.UserDTO;
import com.nutrili.external.database.entity.Patient;
import com.nutrili.external.database.entity.User;
import com.nutrili.Utils.RoleConst;
import com.nutrili.service.*;
import com.twilio.rest.serverless.v1.service.Environment;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.net.InetAddress;
import java.util.UUID;


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

    @Autowired
    OAuth2Service oAuth2Service;

    @Autowired
    FileService fileService;



    @PostMapping(value = "/insertUser")
    public ResponseEntity<?> insertUser(@RequestHeader(value="AOBARIZATION",required = true) String authorization,  @RequestBody UserDTO userDTO) {
        validateTokenService.validateToken(authorization);
        userDetailsService.insertUser(userDTO);
        return new ResponseEntity<String>("User was created successfully",HttpStatus.OK);

    }

    @GetMapping(value= "/getUser")
    @Secured({RoleConst.ROLE_NUTRITIONIST,RoleConst.ROLE_PATIENT})
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok( userDetailsService.getUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    @PostMapping(value="/smsToken")
    public ResponseEntity<?> smsToken(@RequestHeader(value="AOBARIZATION",required = true) String authorization, @RequestParam @Valid String phone)
    {
        validateTokenService.validateToken(authorization);
        smsService.generateSmsToken(phone);
        return new ResponseEntity<String>("SMS was sent successfully",HttpStatus.OK);

    }

    @PutMapping(value="/updateUser")
    @Secured({RoleConst.ROLE_NUTRITIONIST,RoleConst.ROLE_PATIENT})
    public ResponseEntity<?> updateUser( @Valid @RequestBody UserDTO userDTO)
    {
        userDetailsService.updateUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),userDTO);
        return new ResponseEntity<String>("User was modified successfully",HttpStatus.OK);
    }

    @PutMapping(value="/updateUserProfilePic")
    @Secured({RoleConst.ROLE_NUTRITIONIST,RoleConst.ROLE_PATIENT})
    public ResponseEntity<?> updateUserProfilePic(@RequestPart(value = "profilePic", required = false) MultipartFile profilePic)
    {
       User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       userDetailsService.setImage(fileService.updateFile(profilePic),user);
        return new ResponseEntity<String>("OK",HttpStatus.OK);
    }

    @GetMapping(value="/isNewUser")
    @Secured({RoleConst.ROLE_PATIENT})
    public ResponseEntity<?> isNewUser()
    {
        NewUserDTO newUserDTO =userDetailsService.isNewUser((Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new ResponseEntity<NewUserDTO>(newUserDTO,HttpStatus.OK);
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
    }*/

    @Secured({RoleConst.ROLE_NUTRITIONIST, RoleConst.ROLE_PATIENT})
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(){
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        oAuth2Service.revokeToken(user);
        return new ResponseEntity<String>("Token was revoked successfully", HttpStatus.OK);
    }


    @Secured({RoleConst.ROLE_NUTRITIONIST, RoleConst.ROLE_PATIENT})
    @RequestMapping(value = "/vruum", method = RequestMethod.GET)
    public ResponseEntity<?> vrum()
    {
        System.out.print((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new ResponseEntity<String>("vrrum",HttpStatus.OK);
    }

    @RequestMapping("/getImage/{fileName}")
    @ResponseBody
    public FileSystemResource getFile(@PathVariable String fileName) throws IOException {
        return new FileSystemResource(new File("./user-photos/"+fileName));
    }

}
