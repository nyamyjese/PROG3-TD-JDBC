package org.TD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static String USER = "product_manager_user";
    public static String PASSWORD = "123456";

    public static Connection getDBConnection() {
        try {
            return DriverManager.getConnection(URL , USER , PASSWORD);
        }
        catch (SQLException e) {
            System.err.println("Connection Failed! Check output console");
            return null;
        }
    }
}
