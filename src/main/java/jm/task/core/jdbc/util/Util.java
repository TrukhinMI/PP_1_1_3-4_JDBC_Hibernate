package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String SHOW_SQL = "true";
    private static final String HBM2DDL_AUTO = "update";

    private static SessionFactory factory;

    public static SessionFactory openConnectionHibernate() {
        if (factory == null) {
            try {
                factory = new Configuration()
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.driver_class", DRIVER)
                        .setProperty("hibernate.connection.username", USER)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.dialect", DIALECT)
                        .setProperty("hibernate.show_sql", SHOW_SQL)
                        .setProperty("hibernate.current_session_context_class", "thread")
                        .setProperty("hibernate.hbm2ddl.auto", HBM2DDL_AUTO)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
                System.out.println("Done!");
            } catch (RuntimeException e) {
                System.err.println("SessionFacrory не был создан" + e.getMessage());
            }
        }

        return factory;
    }


    public static Connection openConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.printf("Ошибка БД");
            e.printStackTrace();
        }

        return connection;
    }


}
