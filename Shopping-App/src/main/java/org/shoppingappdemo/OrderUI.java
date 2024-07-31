package org.shoppingappdemo;

import javafx.geometry.Insets;
import javafx.scene.control.Label; 
import javafx.scene.layout.VBox; 
import org.shoppingappdemo.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class OrderUI {

    private String loggedInUser;
    private final VBox orderBox;

    private static final String SELECT_ORDERS_QUERY = "SELECT * FROM orders WHERE username = ?";

    public OrderUI() {
        orderBox = new VBox(10);
        orderBox.setPadding(new Insets(10));
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
        loadOrders();
    }

    private void loadOrders() {
        Connection connection = Database.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_QUERY)) {
            statement.setString(1, loggedInUser);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String totalPrice = resultSet.getString("total_price");
                String productsOrdered = resultSet.getString("products_ordered");
                String date = resultSet.getString("order_date");
                DecimalFormat df = new DecimalFormat("#.00");

                Label orderLabel = new Label(date+" " +" Cost: $" + df.format(Double.parseDouble(totalPrice)) + " "+ " " + productsOrdered + " " );
                orderLabel.setPadding(new Insets(5));
                 orderLabel.setStyle("-fx-border-color: white; -fx-border-width: 3px; -fx-background-color: #D1FFC6;");        
                orderBox.getChildren().add(orderLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public VBox getOrderBox() {
        return orderBox;
    }
}
