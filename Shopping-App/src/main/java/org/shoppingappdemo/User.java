package org.shoppingappdemo;

import org.shoppingappdemo.Database.Database;

import java.sql.*;

public class User {
    private String username;
    private String password;
    private String email;
    private String phoneNo;
    private String fullName;

    public User(String username, String password, String email, String phoneNo) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phoneNo;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //write all setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phoneNo = phone;
    }

    public static User getUser(String username) {
        Connection connection = Database.getConnection();
        try (connection) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve user data from the database

                        String fetchedUsername = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String email = resultSet.getString("email");
                        String phoneNo = resultSet.getString("phoneNo");

                        return new User(fetchedUsername, password, email, phoneNo);
                    }
                }
            }
        } catch (SQLException e) {
            // Log or print the exception
            e.printStackTrace();
            throw new RuntimeException("Error fetching user data from the database.", e);
        }
        return new User("missing", "password", "email", "phoneNo");
    }

}
