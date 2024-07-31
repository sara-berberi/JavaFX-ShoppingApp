package org.shoppingappdemo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.shoppingappdemo.BrowseProducts.ProductTableViewApp;

import java.io.IOException;

public class UserDashboardController {
    @FXML
    private Button myProfile;
    @FXML
    private Button myOrders;
    @FXML
    private Button browse;
    @FXML
    private Button logOut;
    @FXML
    private StackPane AdditionalUIHolder;

    private Main mainApplication;

    public void initialize() {
        logOut.setOnMouseClicked(event -> handleLogOutClick());
        myOrders.setOnMouseClicked(event -> handleMyOrdersClick());
        browse.setOnMouseClicked(event -> handleBrowseProductsClick());
        myProfile.setOnMouseClicked(event -> handleMyProfileClick());
    }

    public void handleLogOutClick() {
        mainApplication.showLoginPage(mainApplication.getPrimaryStage());
    }

    public void handleMyOrdersClick() {
        loadMyOrdersPage();
    }

    private void loadMyOrdersPage() {
        OrderUI orderUI = new OrderUI();
        orderUI.setLoggedInUser(UserSession.getLoggedInUsername());

        AdditionalUIHolder.getChildren().clear();
        AdditionalUIHolder.getChildren().add(orderUI.getOrderBox());
    }


    public void handleBrowseProductsClick() {
        loadProductTableView(); // Load the ProductTableView within the AdditionalUIHolder
    }

    private void loadProductTableView() {

        AdditionalUIHolder.getChildren().clear();
        AdditionalUIHolder.getChildren().add(ProductTableViewApp.createProductTableView());

    }


    public void handleMyProfileClick() {
        loadMyProfile();
    }

    private void loadMyProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyProfile.fxml"));
            AnchorPane myProfileRoot = loader.load();

            MyProfile myProfileController = loader.getController();
            myProfileController.setMainApplication(mainApplication);
            myProfileController.populateWithUserData();
            myProfileController.modifyDataContainer();

            AdditionalUIHolder.getChildren().clear();
            AdditionalUIHolder.getChildren().add(myProfileRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }
}
