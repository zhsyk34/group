package com.cat.entity;

import com.cat.persistence.annotation.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter

@Entity
@Comment("学生")
public class Student extends Common {
    @Column(nullable = false, length = 30)
    @Comment("姓名")
    private String name;
    @Comment("年龄")
    private short age;
    @Comment("优等")
    private boolean good;
}
