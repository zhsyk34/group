package com.cat.entity.auth;

import com.cat.dict.GenderEnum;
import com.cat.entity.Common;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 登录后通过 {@link UserGateway} 查询可以访问的gateway
 */

@Getter
@Setter

@Entity
public class User extends Common {

    /**
     * 登录方式一
     */
    @Column(nullable = false, length = 30)
    private String username;
    @Column(nullable = false, length = 60)
    private String password;

    /**
     * 登录方式二
     */
    @Column(nullable = false)
    private String phone;

    /**
     * 是否启用:暂未支持
     */
    @Column
    private boolean enabled;

    /**
     * 详细信息
     */
    @Column(length = 18)
    private String idCard;
    @Column(nullable = false, length = 20)
    private String name;
    private GenderEnum gender;
    private String email;
    private String remark;
}
