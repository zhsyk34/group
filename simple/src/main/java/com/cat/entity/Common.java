package com.cat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class Common {
    @Id
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();
    @Column(insertable = false)
    private LocalDateTime updateTime;
}
