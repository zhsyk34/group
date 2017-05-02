package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(name = "uk_sn", columnNames = {"sn"})}
)
public class Gateway extends Common {
    @Column(unique = true, nullable = false, updatable = false, length = 30)
    private String sn;
    @Column(unique = true, nullable = false, updatable = false, length = 40)
    private String udid;
    @Column(nullable = false, length = 60)
    private String appId;
    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String type;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_gateway_gateway_version"))
    private GatewayVersion gatewayVersion;
}
