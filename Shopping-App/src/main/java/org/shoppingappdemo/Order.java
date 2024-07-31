package org.shoppingappdemo;

import org.shoppingappdemo.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order{
    public int id;
    public String usernameOfBuyer;
    public double totalPrice;
    public String productsOrdered; //comma separated products
    public String status; //pending, shipped, delivered

    public Order(int id, String usernameOfBuyer, double totalPrice, String productsOrdered, String status) {
        this.id = id;
        this.usernameOfBuyer = usernameOfBuyer;
        this.totalPrice = totalPrice;
        this.productsOrdered = productsOrdered;
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public String getUsernameOfBuyer() {
        return usernameOfBuyer;
    }

    public String getProductsOrdered() {
        return productsOrdered;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public static int getLastAssignedID() throws SQLException {
        int lastID = 0;

        Connection connection = Database.getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement("SELECT MAX(order_id) FROM orders");
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastID = resultSet.getInt(1);
            }
        }

        return lastID;
    }

    public static int generateID() throws SQLException {
        return getLastAssignedID() + 1;
    }

    public void save() {
        Connection connection = Database.getConnection();

        try (connection) {
            String query = "INSERT INTO orders (order_id, username, total_price, products_ordered, statusOfPackage) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, usernameOfBuyer);
                preparedStatement.setDouble(3, totalPrice);
                preparedStatement.setString(4, productsOrdered);
                preparedStatement.setString(5, status);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}