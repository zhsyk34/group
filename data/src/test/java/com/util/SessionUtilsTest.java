package com.util;

import com.cat.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SessionUtilsTest {

    private EntityManager manager;
    private EntityTransaction transaction;

    @Before
    public void before() throws Exception {
        manager = SessionUtils.entityManager();
        System.out.println(manager);
        transaction = manager.getTransaction();
        transaction.begin();
    }

    @Test
    public void init() throws Exception {

    }

    @Test
    public void save() throws Exception {
        User user = new User().setName("hello");
        manager.persist(user);
        System.out.println(user.getId());
    }

    @After
    public void after() throws Exception {
        transaction.commit();
        manager.close();
    }
}