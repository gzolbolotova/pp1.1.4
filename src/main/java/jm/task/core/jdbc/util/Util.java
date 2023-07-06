package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private  static final String URL = "jdbc:mysql://localhost:3306/bdforpp";
    private static final String USER = "root1";
    private static final String PASSWORD = "root1";
    public static Connection getConnection(){
        Connection connection = null;
        try {


            connection= DriverManager.getConnection(
                    URL,USER,PASSWORD);
           //System.out.println("Подключено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

}
