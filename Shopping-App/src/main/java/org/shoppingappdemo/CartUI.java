package org.shoppingappdemo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartUI {

    public static void displayCart(ObservableList<Product> selectedProducts, String username) {
        Stage cartStage = new Stage();
        cartStage.setTitle("Shopping Cart");

        // Set the quantity of each product to 1
        selectedProducts.forEach(product -> product.SetQuantityP(1));

        TableView<Product> tableView = new TableView<>(FXCollections.observableArrayList(selectedProducts));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));

        TableColumn<Product, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty());

        TableColumn<Product, Void> buttonColumn = new TableColumn<>("Actions");
        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button incrementButton = new Button("+");
            private final Button decrementButton = new Button("-");
            private final Button removeButton = new Button("Remove");

            {
                incrementButton.setOnAction(event -> incrementQuantity());
                decrementButton.setOnAction(event -> decrementQuantity());
                removeButton.setOnAction(event -> removeItem());
            }

            private void incrementQuantity() {
                Product product = getTableView().getItems().get(getIndex());
                product.SetQuantityP(product.getQuantityP() + 1);
            }

            private void decrementQuantity() {
                Product product = getTableView().getItems().get(getIndex());
                if (product.getQuantityP() > 1) {
                    product.SetQuantityP(product.getQuantityP() - 1);
                } else {
                    showConfirmationDialog("Are you sure you want to remove this item from the cart?", this::removeItem);
                }
            }

            private void removeItem() {
                getTableView().getItems().remove(getIndex());
            }

          

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5, incrementButton, decrementButton, removeButton);
                    setGraphic(hBox);
                }
            }
        });

        Button generateOrderButton = new Button("Generate Order");
         generateOrderButton.setOnAction(event -> {
            List<Product> products = new ArrayList<>(tableView.getItems()); // Get the products in the cart
            try {
                saveOrder(username, products);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cartStage.close();
        });

        tableView.getColumns().addAll(nameColumn, quantityColumn, buttonColumn);

        VBox cartLayout = new VBox(10, tableView, generateOrderButton);
        Scene scene = new Scene(cartLayout, 300, 200);
        cartStage.setScene(scene);
        cartStage.show();
    }
    
      private static void showConfirmationDialog(String message, Runnable onConfirmation) {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        onConfirmation.run();
                    }
                });
            }

    public static void saveOrder(String username, List<Product> products) throws SQLException {
        double totalPrice = 0; StringBuilder allProducts = new StringBuilder();
        for (Product product : products) {
            int quantity = product.getQuantityP(); // Get the quantity chosen by the user
            // We can now use the quantity and other product details to save the order
            // For example, we might want to save the product ID, name, price, and quantity in a database
             totalPrice = totalPrice + product.getPrice() * quantity;
          allProducts.append(product.getProductName()).append(" x ").append(quantity).append(", ").append("\n");
        }
        String totalProducts = allProducts.toString();
        totalProducts = totalProducts.substring(0, totalProducts.length() - 2); // Remove the last comma and space

        System.out.println("Created Order: " + username + " " + totalPrice + " " + totalProducts);
        // Create a new order instance
        Order order = new Order(Order.generateID(), username, totalPrice, totalProducts, "Pending");
        // Save the order to the database
        order.save();
        showConfirmationDialog("Order placed successfully. Go to My Orders to view all your order.", Color.LIGHTGREEN);



    }

    static void showConfirmationDialog(String message, Color color) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setTextFill(Color.BLACK);
        okButton.setStyle("-fx-background-color: " + toHex(color) + ";");
        // Wait for the user to close the dialog
        alert.showAndWait();
    }

    private static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }



}
