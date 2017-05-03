package com.cat.entity;

import com.cat.persistence.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
@Comment("网关")
public class Gateway extends Common {
    @Column(nullable = false, updatable = false, length = 30)
    private String sn;
    @Column(nullable = false, updatable = false, length = 40)
    private String udid;
    @Column(nullable = false, length = 60)
    private String appId;
    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    @Comment("网关主版本号")
    private String major;//GatewayVersion for select gatewayVersionId

    private long gatewayVersionId;
}
