package com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ManagerUtils {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        //persistence-unit:name
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("app");
    }

    public static EntityManager manager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
