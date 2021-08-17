package com.nutrili.external.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nutrili.external.database.entity.Role;
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

@Getter
@Setter
public class UserDTO {

    @NotNull
    private boolean isNutritionist;

    private double height;

    private double weight;

    private int score;

    private int crn;

    @NotNull
    @Size(min=6, max=30)
    private String name;

    @NotNull
    @Size(max=1)
    private String gender;

    @NotNull
    private Date birth;

    @NotNull
    @Size(min=13,max=13)
    private String phone;

    @NotNull
    @CPF
    private String cpf;

    @Email
    @NotNull
    private String email;

    @Size(min=5,max=30)
    @NotNull
    private String password;

    @NotNull
    private String image;

    @NotNull
    @Pattern(regexp="^[0-9]{5}-[0-9]{3}")
    @Size(min=9,max = 9)
    private String cep;

    @NotNull
    @Size(min=2,max=2)
    private String state;

    @Size(min= 1)
    @NotNull
    private String city;

    @Size(min= 1)
    @NotNull
    private String neighborhood;

    @Size(min=1)
    @NotNull
    private String street;

    @Size(min=1)
    @NotNull
    private String number;

}
