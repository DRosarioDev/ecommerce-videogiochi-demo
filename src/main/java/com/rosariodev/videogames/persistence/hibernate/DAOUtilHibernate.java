package com.rosariodev.videogames.persistence.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.videogames.persistence.DAOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Profile("hibernate")
public class DAOUtilHibernate {
    
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;
    
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getCurrentSession() throws DAOException {
        try{
            return sessionFactory.getCurrentSession();
        }catch(HibernateException ex){
            log.error(ex.getLocalizedMessage());
            throw new DAOException("Error getting current session", ex);
        }
    }

    public static void beginTransaction() throws DAOException{
        try{
            sessionFactory.getCurrentSession().beginTransaction();
        }catch(HibernateException ex){
            log.error(ex.getLocalizedMessage());
            throw new DAOException("Error beginning transaction", ex);
        }
    }

    public static void commitTransaction() throws DAOException{
        try{
            sessionFactory.getCurrentSession().getTransaction().commit();
        }catch(HibernateException ex){
            log.error(ex.getLocalizedMessage());
            throw new DAOException("Error committing transaction", ex);
        }
    }

    public static void rollbackTransaction() throws DAOException{
        try{
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }catch(HibernateException ex){
            log.error(ex.getLocalizedMessage());
            throw new DAOException("Error rolling back transaction", ex);
        }
    }
}
