package com.nutrili.external.database.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="USER_NUTRILI")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@NotNull
    @Setter(value= AccessLevel.NONE)
    private Long id;

    @Column(name = "nameUser")
    //@NotNull
    //@Size(min=6, max=30)
    private String name;

    @Column(name= "gender")
    //@NotNull
    //@Size(max=1)
    private String gender;

    @Column(name="birth")
    //@NotNull
    private Date birth;

    @Column(name="phone")
    //@NotNull
    //@Size(min=11,max=11)
    private String phone;

    @Column(name="CPF")
    //@NotNull
    //@CPF
    private String cpf;

    @Column(name= "email")
    //@Email
    //@NotNull
    private String email;

    @JsonIgnore
    @Column(name="passwordUser")
    //@NotNull
    private String password;

    @Column(name="linkImage")
    //@NotNull
    private String image;

    //mudar
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinTable(name="user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private List<Role> roles;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address addressId;

    public User() {
    }

    public User(String name, String email){
        super();
        this.name = name;
        this.email = email;
    }
    public User(User user) {
        super();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.id = user.getId();
    }
    public User(String name, String email, String password, List<Role> roles) {
        super();
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
