package com.cat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(name = "type_number", columnList = "type,number", unique = true)})
public class GatewayVersion extends Common {
    @Column(nullable = false, length = 30)
    private String type;//Z-GT-S01
    @Column(unique = true, nullable = false, length = 10)
    private String number;//v1.3
    @Column(unique = true, nullable = false, length = 10)
    private String info;//Z-GT-S01-V01-160725
    @Column(unique = true, nullable = false, length = 10)
    private String downloadAddress;//http://114.55.128.37:8088/download/V1.3/Z-GT-S01-V01-160725
}
