package com.nutrili.external.DTO;


import com.nutrili.external.database.entity.Address;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDTO {

    private boolean nutritionist;

    private Double height;

    private Double weight;

    private String crn;

    private String crnType;

    @Size(min=1, max=30)
    private String name;

    @Size(min=1)
    private String gender;

    private Date birth;

    @Size(min=11,max=11)
    private String phone;

    @CPF
    private String cpf;

    @Email
    private String email;

    @Size(min=5,max=30)
    private String password;

    private String image;

    private Address personalAddress;

    private Address officeAddress;

}
