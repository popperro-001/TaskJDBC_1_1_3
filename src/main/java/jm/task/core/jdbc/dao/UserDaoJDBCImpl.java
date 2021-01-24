package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER not NULL AUTO_INCREMENT, " +
                    "name VARCHAR(255) not NULL, " +
                    "lastName VARCHAR(255) not NULL, " +
                    "age INTEGER not NULL, " +
                    "PRIMARY KEY (id))";
            statement.executeUpdate(sql);
            System.out.println("table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            String sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);
            System.out.println("table was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – "+name+" добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User с ID – "+id+" удален из базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            String sql = "SELECT id, name, lastName, age FROM user";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement()){
            String sql = "DELETE FROM user";
            statement.executeUpdate(sql);
            System.out.println("table was cleared");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
