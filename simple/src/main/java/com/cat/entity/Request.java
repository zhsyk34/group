package com.cat.entity;

import com.cat.dict.RequestStatusEnum;
import com.cat.persistence.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class Request {
    @Id
    private long id;

    @Column(nullable = false, updatable = false, length = 36)
    @Comment("回执编号,用以异步查询")
    private String ackNo;

    @Column(nullable = false, updatable = false, length = 36)
    @Comment("锁序列号(lock-uuid)或(入网时)网关序列号(gateway-udid)")
    private String deviceNo;
    @Column(nullable = false, updatable = false)
    @Comment("请求内容")
    private String content;//队列异步执行,用以查看记录
    @Column(nullable = false, updatable = false, length = 36)
    @Comment("确认请求者身份")
    private String appId;
    @Column
    private String callbackUrl;//请求提供的回调地址==>主动推送

    @Column(nullable = false, updatable = false)
    private LocalDateTime acceptTime;
    @Column(insertable = false)
    private LocalDateTime submitTime;
    @Column(insertable = false)
    private LocalDateTime finishTime;//反馈时间
    @Column(insertable = false)
    private LocalDateTime callbackTime;//(通过回调进行)推送时间

    private RequestStatusEnum status = RequestStatusEnum.ACCEPT;
}
