package org.example;



import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil
{
    private static SessionFactory sessionFactory;

    static
    {
        if(sessionFactory == null)
        {
            try {
                Configuration cfg = new Configuration().configure("Hibernate.cfg.xml").addAnnotatedClass(Task.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
                sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

}
