package com.nutrili.external.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.nutrili.external.database.entity.Address;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
public class UserDTO {

    private boolean nutritionist;

    private UUID patientID;

    private Double height;

    private Double weight;

    private String crn;

    private String crnType;

    @Size(min=1, max=60)
    private String name;

    @Size(min=1)
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
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
