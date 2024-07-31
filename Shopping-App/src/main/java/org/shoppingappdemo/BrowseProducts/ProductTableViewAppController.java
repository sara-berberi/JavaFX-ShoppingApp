package org.shoppingappdemo.BrowseProducts;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.shoppingappdemo.Main;
import org.shoppingappdemo.Product;

public class ProductTableViewAppController {
    @FXML
    private TableView<Product> tableView;

    private Main mainApplication;

    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void initialize() {
        // Customize your table view if needed
    }
}
