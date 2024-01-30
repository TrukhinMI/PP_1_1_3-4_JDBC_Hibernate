package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private final Connection connection = Util.openConnection();


    public void createUsersTable() {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS user(" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(30) NOT NULL ," +
                    "lastName VARCHAR(30) NOT NULL ," +
                    "age SMALLINT NOT NULL );" +
                    "" +
                    "")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы user" + e.getMessage());
        }
        System.out.println("Таблица user создана");
    }

    public void dropUsersTable() {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DROP TABLE IF EXISTS user;")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы user" + e.getMessage());
        }
        System.out.println("Таблица user удалена");
    }


    public void saveUser(String name, String lastName, byte age) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO user(name, lastName, age) " +
                    "VALUES (?, ?, ?);")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя" + e.getMessage());
        }
        System.out.println("Пользователь добавлен");
    }


    public void removeUserById(long id) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    DELETE FROM user 
                    WHERE id = ?
                    """)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя " + e.getMessage());
        }
        System.out.println("Пользователь удален");
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(""" 
                    SELECT id, name, lastName, age
                    FROM user;""")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    userList.add(new User(
                            resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            resultSet.getByte("age")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных их таблицы user" + e.getMessage());
        }
        System.out.println("Данные из таблицы user получены");
        return userList;
    }

    public void cleanUsersTable() {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    TRUNCATE user;
                    """)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы user" + e.getMessage());
        }
        System.out.println("Таблица user очищена");
    }
}