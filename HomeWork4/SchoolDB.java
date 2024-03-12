package ru.geekbrains.homework4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SchoolDB {
//    private  static final String URL = "jdbc:mysql://localhost:3306";
//    private static final String USER = "root";
//    private static final String PASSWORD = "**********";


    public static void addCourse() {
        Connector connector = new Connector();
        Session session = connector.getSession();
        Courses course = new Courses("Biology", 120);
        session.beginTransaction();
        session.save(course);
        course = new Courses("History", 60);
        session.save(course);
        course = new Courses("English", 60);
        session.save(course);
        course = new Courses("Geo", 90);
        session.save(course);
        course = new Courses("Physics", 120);
        session.save(course);
        session.getTransaction().commit();
        session.close();
    }

    public static void readCourses() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            List<Courses> courses = session.createQuery("FROM Courses", Courses.class).getResultList();
            courses.forEach(System.out::println);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void changeCourses() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            String hql = "FROM Courses where id =:id";
            Query<Courses> query = session.createQuery(hql, Courses.class);
            query.setParameter("id", 7);
            Courses course = query.getSingleResult();
            System.out.println(course);
            course.setTitle("Sport");
            course.setDuration(45);
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
            System.out.println(course);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteCourse() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            session.beginTransaction();

            String hql = "FROM Courses where id =:id";
            Query<Courses> query = session.createQuery(hql, Courses.class);
            query.setParameter("id", 5);
            Courses course = query.getSingleResult();
            System.out.println(course);
            session.delete(course);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllCourses() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            session.beginTransaction();

            List<Courses> courses = session.createQuery("FROM Courses", Courses.class).getResultList();
            courses.forEach(session::delete);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

