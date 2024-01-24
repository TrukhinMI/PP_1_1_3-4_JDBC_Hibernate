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
    public UserDaoJDBCImpl() {
    }

    private static final String create_table_sql = """
            CREATE TABLE IF NOT EXISTS user(
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(30) NOT NULL ,
                lastName VARCHAR(30) NOT NULL ,
                age SMALLINT NOT NULL 
            );
            """;
    private static final String drop_table_sql = """
            DROP TABLE IF EXISTS user;
            """;
    private static final String save_sql = """
            INSERT INTO user(name, lastName, age) 
            VALUES (?, ?, ?);
            """;
    private static final String delete_sql = """
            DELETE FROM user
            WHERE id = ?
            """;
    private static final String find_all_users = """
            SELECT id, name, lastName, age
            FROM user;
            """;
    private static final String clean_table_sql = """
            TRUNCATE user;
            """;

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(create_table_sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(drop_table_sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(save_sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(delete_sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(find_all_users)) {
            ResultSet resultSet = preparedStatement.executeQuery(); // выполняем запрос и получаем данные из запроса
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.openConnection().prepareStatement(clean_table_sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
