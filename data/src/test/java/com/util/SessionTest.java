package com.util;

import com.cat.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SessionTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void before() throws Exception {
        session = SessionUtils.session();
        System.out.println(session);
        transaction = session.getTransaction();
        transaction.begin();
    }

    @Test
    public void init() throws Exception {
        System.out.println(session);
    }

    @Test
    public void save() throws Exception {
        int r = session.createNamedQuery("User4.save")
                .setParameter(1, "name2")
                .executeUpdate();
        System.out.println(r);
    }

    @Test
    public void save42() throws Exception {
        int r = session.createNamedQuery("User42.save")
                .setParameter("name", "name2")
                .executeUpdate();
        System.out.println(r);
    }

    @Test
    public void save421() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "HeYu");
        int r = session.createNamedQuery("User42.save")
                .setProperties(map)
                .executeUpdate();
        System.out.println(r);
    }

    @Test
    public void save2() throws Exception {
        int r = session.createNamedQuery("User.save")
                .setParameter("name", "new-name")
                .setParameter("id", 1)
                .executeUpdate();
        System.out.println(r);

    }

    @Test
    public void get() throws Exception {
        List<User> list = session.createQuery("from User", User.class).getResultList();
        Optional.ofNullable(list).ifPresent(data -> data.forEach(System.out::println));
    }

    @Test
    public void list() throws Exception {
        List<User> list = session.createNamedQuery("User.list", User.class)
                .setParameter("name", "%o%")
                .getResultList();
        Optional.ofNullable(list).ifPresent(data -> data.forEach(System.out::println));
    }

    @Test
    public void find() throws Exception {
        Optional.ofNullable(session.createNamedQuery("User.list2", User.class)
                .setParameter("name", "a")
                .getResultList()).ifPresent(list -> list.forEach(System.out::println));
    }

    @Test
    public void find4() throws Exception {
        Optional.ofNullable(session.createNamedQuery("User4.find", User.class)
                .setParameter("name", "a")
                .list())
                .ifPresent(list -> list.forEach(System.out::println));
    }

    @Test
    public void find42() throws Exception {
        Optional.ofNullable(session.createNamedQuery("User4.get", User.class)
                .setParameter("name", "w")
                .list())
                .ifPresent(list -> list.forEach(System.out::println));
    }

    @After
    public void after() throws Exception {
        transaction.commit();
        session.close();
    }
}
