package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
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
            statement.execute(sqlCommand);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sqlCom = String.format("DROP TABLE %s", users);
        try {
            connection.createStatement().executeUpdate(sqlCom);
        } catch (SQLException e) {
        }

    }

    public void saveUser(String name, String lastName, byte age) {
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
        String sql = String.format("DELETE FROM %s WHERE id = ?", users);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", users);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String lastName = resultSet.getString("LASTNAME");
                Byte age = resultSet.getByte("AGE");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

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
