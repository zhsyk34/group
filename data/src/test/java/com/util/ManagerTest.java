package com.util;

import com.cat.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ManagerTest {

    private EntityManager manager;
    private EntityTransaction transaction;

    @Before
    public void before() throws Exception {
        manager = ManagerUtils.manager();
        System.out.println(manager);
        transaction = manager.getTransaction();
        transaction.begin();
    }

    @Test
    public void init() throws Exception {

    }

    @Test
    public void save() throws Exception {
        User user = new User().setName("hello2");
        manager.persist(user);
        System.out.println(user.getId());
    }

    @Test
    public void find() throws Exception {
        List<User> list = manager.createNamedQuery("listUser").getResultList();
        Optional.ofNullable(list).ifPresent(data -> data.forEach(System.out::println));
    }

    @Test
    public void find2() throws Exception {
        Optional.ofNullable(manager.createNamedQuery("listUser2").getResultList()).ifPresent(list -> list.forEach(System.out::println));
    }

    @After
    public void after() throws Exception {
        transaction.commit();
        manager.close();
    }
}