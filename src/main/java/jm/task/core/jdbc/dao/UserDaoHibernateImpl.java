package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory factory = Util.openConnectionHibernate();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                            "id TINYINT PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(50)," +
                            "lastname VARCHAR(50)," +
                            "age INT" +
                            ");")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка создания таблицы user" + he.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE user")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка удаления таблицы user" + he.getMessage());
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.getCurrentSession();
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка добавления пользователя" + he.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка удаления пользователя " + he.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = null;
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();

            for (User user : userList) {
                System.out.println(user);
            }
        } catch (Exception he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка получения данных их таблицы user" + he.getMessage());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка очистки таблицы user" + he.getMessage());
        }
    }
}
