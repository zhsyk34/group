package com.cat.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@Table(indexes = {
        @Index(name = "uk_major_minor", columnList = "major, minor", unique = true),
        @Index(name = "uk_info", columnList = "info", unique = true),
        @Index(name = "uk_download_address", columnList = "downloadAddress", unique = true),
})
public class GatewayVersion extends Common {
    @Column(nullable = false, length = 30)
    private String major;//Z-GT-S01
    @Column(nullable = false, length = 10)
    private String minor;//v1.3
    @Column(nullable = false, length = 10)
    private String info;//Z-GT-S01-V01-160725
    @Column(nullable = false, length = 100)
    private String downloadAddress;//http://114.55.128.37:8088/download/V1.3/Z-GT-S01-V01-160725
}
