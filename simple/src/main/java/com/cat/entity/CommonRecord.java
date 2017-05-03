package com.cat.entity;

import com.cat.dict.PushTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter

@MappedSuperclass
public class CommonRecord extends Common {
    @Column(nullable = false, updatable = false)
    private long gatewayId;
    @Column(nullable = false, updatable = false)
    private short deviceIndex;
    @Column(nullable = false, updatable = false, length = 30)
    private String gatewaySn;
    @Column(nullable = false, updatable = false, length = 30)
    private String gatewayName;

    @Column(nullable = false, updatable = false)
    private long lockId;
    @Column(nullable = false, updatable = false, length = 36)
    private String lockUuid;
    @Column(nullable = false, updatable = false, length = 30)
    private String lockName;

    private PushTypeEnum pushType;
}
