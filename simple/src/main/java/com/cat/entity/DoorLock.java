package com.cat.entity;

import com.cat.dict.SwitchStateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

import static com.cat.dict.SwitchStateEnum.UNKNOWN;

@Getter
@Setter

@Entity
public class DoorLock extends Common {
    @Column(nullable = false, updatable = false, length = 36)
    private String uuid;//insert->UUID.randomUUID().toString();

    @Column(nullable = false)
    private Long gatewayId;
    @Column(nullable = false)
    private short deviceIndex;

    @Column(nullable = false, length = 30)
    private String name;

    @Column
    private boolean available;

    @Column(nullable = false, length = 10)
    private String superPassword;
    @Column(nullable = false, length = 10)
    private String tempPassword;

    @Column
    private SwitchStateEnum locked = UNKNOWN;
    @Column
    private SwitchStateEnum upLocked = UNKNOWN;
    @Column
    private SwitchStateEnum backLocked = UNKNOWN;
    @Column
    private SwitchStateEnum online = UNKNOWN;
    @Column(nullable = false)
    private int voltage = UNKNOWN.getCode();

    @Column(insertable = false)
    private LocalDateTime pushTime;
}
