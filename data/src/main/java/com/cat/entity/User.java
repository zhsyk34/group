package com.cat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

}
