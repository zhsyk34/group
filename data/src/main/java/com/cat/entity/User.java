package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

//@NamedNativeQueries({
//        @NamedNativeQuery(
//                name = "listUser",
//                query = "SELECT * FROM USER",
//                resultClass = User.class
//        )
//})
//@NamedQueries({
//        @NamedQuery(
//                name = "listUser2",
//                query = "FROM User"
//        )
//})

@Getter
@Setter
@Accessors(chain = true)
@ToString

@Entity
//alter table User add constraint uk_name_age unique (name, age)
@Table(indexes = {@Index(name = "uk_name_age", columnList = "name, age", unique = true)})

//alter table User  add constraint uk_name_age unique (name, age)
//@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_name_age", columnNames = {"name", "age"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30)
    private String name;

    private short age;

}
