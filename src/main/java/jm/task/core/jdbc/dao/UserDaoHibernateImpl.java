package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER not NULL AUTO_INCREMENT," +
                    "name VARCHAR(255) not NULL," +
                    "lastName VARCHAR(255) not NULL," +
                    "age INTEGER not NULL," +
                    "PRIMARY KEY (id))").executeUpdate();
            session.close();
            System.out.println("table created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.close();
            System.out.println("table was deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем – "+name+" добавлен в базу данных");
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try{
            User user;
            Session session = getSessionFactory().openSession();
            user = (User) session.load(User.class, id);
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("User с ID – "+id+" удален из базы данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Session session = getSessionFactory().openSession();
            userList = session.createCriteria(User.class).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            List<User> userList = session.createCriteria(User.class).list();
            for (User user: userList){
                session.delete(user);
            }
            session.getTransaction().commit();
            session.close();
            System.out.println("table was cleared");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
