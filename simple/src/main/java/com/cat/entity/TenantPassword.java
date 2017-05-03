package com.cat.entity;

import com.cat.persistence.annotation.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder

@Entity
public class TenantPassword extends Common {
    @Column
    private long lockId;
    @Comment("租客密码编号,2-98")
    private int ordinal;
    @Column(nullable = false, length = 10)
    private String value;
    private String remark;//密码备注(如使用者信息等)
}
