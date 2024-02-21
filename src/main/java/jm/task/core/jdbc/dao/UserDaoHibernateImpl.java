package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory factory = Util.openConnectionHibernate();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
//        Session session = factory.getCurrentSession();
//        try {
//            session.beginTransaction();
//            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
//                            "id INT PRIMARY KEY AUTO_INCREMENT," +
//                            "name VARCHAR(50)," +
//                            "lastname VARCHAR(50)," +
//                            "age INT" +
//                            ");")
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception he) {
//            if (session != null) {
//                session.getTransaction().rollback();
//            }
//            System.out.println("Ошибка создания таблицы user" + he.getMessage());
//        } finally {
//            factory.close();
//        }
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
        } finally {
            factory.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
