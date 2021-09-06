
package com.nutrili.external.database.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "phoneToken")
public class SmsToken {
    @Column(name = "tokenId")
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="number")
    @NotNull
    private String number;

    @Column(name = "tokenCode")
    @NotNull
    @Size(min = 6,max=6)
    private String code;

    @Column(name = "createTime")
    @NotNull
    private long createTime;
}