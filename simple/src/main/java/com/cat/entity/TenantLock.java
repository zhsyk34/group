package com.cat.entity;

import com.cat.persistence.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter

@Entity
@Comment("租客租赁(门锁)信息")
public class TenantLock {
    private long tenantId;
    private long lockId;
}
