package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TaskDAO
{
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Task task = new Task();
    //Add
    public void saveTask(Task task)
    {
       Transaction tx = null;
            try(Session session = sessionFactory.openSession())
            {
                tx = session.beginTransaction();
                session.persist(task);
                tx.commit();
                System.out.println("Task Saved Successfully");
            }

    }

    //Read

    //Update

    //Delete
}
