
package com.nutrili.external.database.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "phoneToken")
public class SmsToken {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId", updatable = false, unique = true, nullable = false)
    @Setter(value= AccessLevel.NONE)
    private UUID id;

    @Column(name="number")
    @NotNull
    private String number;

    @Column(name = "tokenCode")
    @NotNull
    @Size(min = 6,max=6)
    private String code;

    @Column(name = "createTime")
    @NotNull
    private Date createTime;
}