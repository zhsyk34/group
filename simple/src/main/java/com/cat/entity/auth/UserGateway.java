package com.cat.entity.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
public class UserGateway {
    @Column
    private long userId;
    @Column
    private long gatewayId;
}
