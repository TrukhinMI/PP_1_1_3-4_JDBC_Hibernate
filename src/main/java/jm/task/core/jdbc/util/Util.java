package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USER = "root";
    private static final String PASSWORD = "root";



    public static Connection openConnection()  {
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
