package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * hibernate:https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#annotations-hibernate-fetch
 * <p>
 * mapping:https://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm
 * <p>
 * mysql int size:https://dev.mysql.com/doc/refman/5.7/en/integer-types.html
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString

@Entity(name = "dnk_lock")
public class Lock extends GeneralEntity {
    private String uuid;

    @Column(name = "gateway_id")
    private long gatewayId;
    @Column(name = "device_index")
    @Type(type = "byte")
    private int deviceIndex;
    //private short deviceIndex;

    @Column(nullable = false, length = 30)
    private String name;
    @Column(name = "is_available")
    @Type(type = "yes_no")//boolean default bit ==> char(1)
    private boolean available;

    @Column(name = "push_time")
    private LocalDateTime pushTime;

}
