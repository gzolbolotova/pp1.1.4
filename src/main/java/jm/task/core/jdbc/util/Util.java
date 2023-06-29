package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bdforpp","root1","root1");
           //System.out.println("Подключено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

}
