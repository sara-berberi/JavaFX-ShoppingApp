package org.shoppingappdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginPage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showLoginPage(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-ui.fxml"));
            Parent loginPage = loader.load();

            // Set up the controller
            LoginController loginController = loader.getController();
            loginController.setMainApplication(this);


            // Show the login page
            Scene loginScene = new Scene(loginPage);
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSignUpPage(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up-ui.fxml"));
            Parent signUpPage = loader.load();

            // Set up the controller
            SignUpController signUpController = loader.getController();
            signUpController.setMainApplication(this);

            // Show the signup page
            Scene signUpScene = new Scene(signUpPage);
            primaryStage.setScene(signUpScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUserDashboard(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userDashboard.fxml"));
            Parent userDashboard = loader.load();

            // Set up the controller
            UserDashboardController userDashboardController = loader.getController();
            if (userDashboardController == null) {
                throw new IllegalStateException("Controller not found in userDashboard.fxml");
            }
            userDashboardController.setMainApplication(this);

            // Show the user dashboard
            Scene userDashboardScene = new Scene(userDashboard);
            primaryStage.setScene(userDashboardScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToAdditionalPane(String fxml, Pane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node node = loader.load();
            pane.getChildren().clear();
            pane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();

        }}

    public Stage getPrimaryStage() {
        return primaryStage;
    }


}
