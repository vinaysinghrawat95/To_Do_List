package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TaskDAO
{
    // Save Task
    public void saveTask(Task task)
    {
       Transaction tx = null;
            try(Session session = HibernateUtil.getSessionFactory().openSession())
            {
                tx = session.beginTransaction();
                session.persist(task);
                tx.commit();
                System.out.println("Task saved successfully");
            } catch (Exception e) {
               if(tx != null) tx.rollback();
               e.printStackTrace();
            }
    }

    //Get All Task
    public List<Task> getAllTasks()
    {
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.createQuery("FROM Task",Task.class).list();
        }
    }

    //Get Task by I'd
    public Task getTaskById(long Id)
    {
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            return session.get(Task.class,Id);
        }
    }

    //Update
    public void updateTask(Task task)
    {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            tx = session.beginTransaction();
            session.merge(task);
            tx.commit();
            System.out.println("Task update successfully");
        } catch (Exception e) {
            System.out.println("Task not update!");
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    //Delete Task by Id
    public void deleteTask(long id)
    {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Task task = session.get(Task.class, id);
            if (task != null) {
                tx = session.beginTransaction();
                session.remove(task);
                tx.commit();
                System.out.println("Task delete successfully");
            }else
            {
                System.out.println("Task not found");
            }
        }catch (Exception e)
        {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /* Enum's filtering */

    public List<Task> getTaskByStatus(TaskStatus status)
    {
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            String hql = "FROM Task WHERE status = :status";
            return session.createQuery(hql,Task.class).setParameter("status",status).list();
        }
    }

    public List<Task> getTaskByPriority(PriorityLevel priority)
    {
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            String hql = "FROM Task WHERE priority = :priority";
            return session.createQuery(hql,Task.class).setParameter("priority",priority).list();
        }
    }

    public List<Task> getTaskByStatusAndPriority(TaskStatus status, PriorityLevel priority)
    {
        try(Session session = HibernateUtil.getSessionFactory().openSession())
        {
            String hql = "FROM Task WHERE status = :status AND priority = :priority";
            return session.createQuery(hql,Task.class).setParameter("status",status).setParameter("priority",priority).list();
        }
    }
}
