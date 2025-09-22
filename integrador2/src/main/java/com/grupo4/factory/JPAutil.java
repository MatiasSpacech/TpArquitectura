package com.grupo4.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
     private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("integrador2");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
