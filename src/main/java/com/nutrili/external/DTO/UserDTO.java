package com.nutrili.external.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nutrili.external.database.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class UserDTO {

    @NotNull
    private boolean nutritionist;

    private Double height;

    private Double weight;

    private Integer score;

    private String crn;

    private String crnType;

    @Size(min=6, max=30)
    private String name;

    @Size(max=1)
    private String gender;

    private Date birth;

    @Size(min=13,max=13)
    private String phone;

    @CPF
    private String cpf;

    @Email
    private String email;

    @Size(min=5,max=30)
    private String password;

    private String image;

    @Pattern(regexp="^[0-9]{5}-[0-9]{3}")
    @Size(min=9,max = 9)
    private String cep;

    @Size(min=2,max=2)
    private String state;

    @Size(min= 1)
    private String city;

    @Size(min= 1)
    private String neighborhood;

    @Size(min=1)
    private String street;

    @Size(min=1)
    private String number;

}
