package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String users = "users";
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String sqlCommand = String.format("CREATE TABLE IF NOT EXISTS %s (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(25), age INT)", users);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlCommand);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sqlCom = String.format("DROP TABLE %s", users);
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sqlCom);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
       // User user = new User();
        String save = String.format("INSERT INTO %s (name, lastName, age) VALUES(?, ?, ?)", users);
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        //User user = new User();
        String sql = String.format("DELETE FROM %s WHERE id = %d", users, id);
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = String.format("SELECT ID, NAME, LASTNAME, AGE FROM %s", users);
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


        public void cleanUsersTable() {
        String sql = String.format("TRUNCATE TABLE %s", users);
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
}
