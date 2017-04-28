package com.util;

import com.cat.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class SessionUtils {

    private static final SessionFactory SESSION_FACTORY;

    static {
        //hibernate.cfg.xml
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.xml").build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        //or mapping in hibernate.xml
        metadataSources.addResource("User.xml");
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        SESSION_FACTORY = metadata.getSessionFactoryBuilder().build();
    }

    public static Session session() {
        return SESSION_FACTORY.openSession();
    }

}
