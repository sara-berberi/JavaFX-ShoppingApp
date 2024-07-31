package org.shoppingappdemo.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "sarasara1";
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (  SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
