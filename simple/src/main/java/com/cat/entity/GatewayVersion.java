package com.cat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
public class GatewayVersion extends Common {
    @Column(nullable = false, length = 30)
    private String major;//Z-GT-S01
    @Column(nullable = false, length = 10)
    private String minor;//v1.3
    @Column(nullable = false, length = 10)
    private String info;//Z-GT-S01-V01-160725
    @Column(nullable = false, length = 100)
    private String downloadUrl;//http://114.55.128.37:8088/download/V1.3/Z-GT-S01-V01-160725
}
