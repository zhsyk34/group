package com.cat.entity;

import com.cat.dict.SwitchStateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.cat.dict.SwitchStateEnum.UNKNOWN;

//@Table(uniqueConstraints = {
//        @UniqueConstraint(name = "uk_gateway_index", columnNames = {"gateway_id", "deviceIndex"})
//})
@Getter
@Setter

@Entity
@Table(indexes = {
        @Index(name = "uk_uuid", columnList = "uuid", unique = true),
        @Index(name = "uk_gateway_index", columnList = "gateway_id, deviceIndex", unique = true),
        @Index(name = "uk_gateway_name", columnList = "gateway_id, name", unique = true),
})
public class DoorLock extends Common {
    @Column(nullable = false, length = 36)
    private String uuid = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_door_lock_gateway"))
//    private Long gatewayId;
    private Gateway gateway;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private short deviceIndex;

    private String name;
    @Column(name = "is_available")
    private boolean available;

    @Column(nullable = false)
    private String superPassword;
    @Column(nullable = false)
    private String tempPassword;

    @Column(name = "is_locked")
    private SwitchStateEnum locked = UNKNOWN;
    @Column(name = "is_up_locked")
    private SwitchStateEnum upLocked = UNKNOWN;
    @Column(name = "is_back_locked")
    private SwitchStateEnum backLocked = UNKNOWN;
    @Column(name = "is_online")
    private SwitchStateEnum online = UNKNOWN;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private int voltage = UNKNOWN.getCode();

    @Column(insertable = false)
    private LocalDateTime pushTime;
}
