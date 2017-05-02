package com.cat.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-bean.xml"})
public class FactorTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void build() throws Exception {
        System.out.println(entityManager);
    }
}
