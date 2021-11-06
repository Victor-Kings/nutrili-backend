package com.nutrili.external.database.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {


    public Role(String name) {
        this.name = name;
    }
    public Role() {
    }

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    private String name;


    @Override
    public String getAuthority() {
        return  this.name;
    }
}
