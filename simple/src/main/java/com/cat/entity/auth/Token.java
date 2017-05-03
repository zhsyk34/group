package com.cat.entity.auth;

import com.cat.entity.Common;
import com.cat.persistence.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
@Comment("凭证")
public class Token extends Common {
    @Column(nullable = false, length = 36)
    @Comment("登录身份")
    private String appId;
    @Column(nullable = false, length = 36)
    private String appSecret;
    @Comment("是否终端用户,用以限制入网等终端操作")
    private boolean endUser;

    @Column(nullable = false, length = 30)
    private String name;
    @Column(length = 20)
    private String phone;
    @Column(length = 60)
    private String email;
    @Column(length = 60)
    private String ip;
}
