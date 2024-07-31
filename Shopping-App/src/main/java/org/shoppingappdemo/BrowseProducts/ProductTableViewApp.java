package org.shoppingappdemo.BrowseProducts;

import org.shoppingappdemo.Main;
import org.shoppingappdemo.CartUI;
import org.shoppingappdemo.Product;
import org.shoppingappdemo.UserSession;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ProductTableViewApp extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "sarasara1";


    private static final ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        loadProductsFromDatabase();

        TableView<Product> tableView = createTableView();
        HBox buttonBox = createButtonBox(tableView);


        VBox root = new VBox(tableView, buttonBox); // Add the table view and button box to the root layout
        root.setSpacing(10);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Product Table View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static VBox createProductTableView() {
        loadProductsFromDatabase();

        TableView<Product> tableView = createTableView();
        HBox buttonBox = createButtonBox(tableView);


        VBox root = new VBox(tableView, buttonBox); // Add the table view and button box to the root layout
        root.setSpacing(10);
        return root;
    }

    public void setMainApplication(Main mainApplication) {
    }


    private static void loadProductsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String productName = resultSet.getString("product_name");
                    String shortDescription = resultSet.getString("short_description");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    Product product = new Product(id, productName, shortDescription, price, quantity);
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static TableView<Product> createTableView() {
        TableView<Product> tableView = new TableView<>(productList);
        tableView.setEditable(true);
        TableColumn<Product, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(data -> data.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));

        // Make the checkbox column editable
        selectColumn.setEditable(true);
        selectColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setSelected(event.getNewValue());
        });

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Short Description");
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getShortDescription()));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.getColumns().addAll(selectColumn, nameColumn, descriptionColumn, priceColumn);

        return tableView;
    }


    private static HBox createButtonBox(TableView<Product> tableView) {
        Button addToCartButton = new Button("Add To Cart");
        addToCartButton.setOnAction(event -> addToCart(tableView));

        return new HBox(addToCartButton);
    }


    private static void addToCart(TableView<Product> tableView) {

        ObservableList<Product> selectedProducts = tableView.getItems().filtered(Product::isSelected);

        // Open the Cart UI and pass the selected products
        CartUI.displayCart(selectedProducts, UserSession.getLoggedInUsername() );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
