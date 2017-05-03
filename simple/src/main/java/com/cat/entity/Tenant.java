package com.cat.entity;

import com.cat.dict.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
public class Tenant extends Common {
    @Column(nullable = false, length = 18)
    private String iDCard;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String phone;
    private GenderEnum gender;
    @Column(length = 50)
    private String email;
}
