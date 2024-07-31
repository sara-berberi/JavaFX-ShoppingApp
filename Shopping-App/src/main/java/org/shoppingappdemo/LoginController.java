package org.shoppingappdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.shoppingappdemo.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    Main mainApplication;
    @FXML
    private Hyperlink registerHere;
    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void initialize() {
        loginButton.setOnAction(event -> {
            try {
                handleLoginButtonClick();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
        registerHere.setOnAction(event -> handleRegisterHereClick());
    }

    public boolean equalsDBinfo(String username, String password) throws SQLException {
        Connection connection = Database.getConnection();
        try (connection) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If there is a match in the database, return true
                }
            }
        }
    }

    public void handleLoginButtonClick() throws SQLException, ClassNotFoundException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (equalsDBinfo(username, password) && usernameValidation(username)) {
            showConfirmationDialog("Login successful");
            UserSession.setLoggedInUsername(username);
            mainApplication.showUserDashboard((Stage) loginButton.getScene().getWindow());


        }
        else if (!usernameValidation(username)) {
            showConfirmationDialog("Invalid username format");
        }
        else {
            showConfirmationDialog("Login failed");
        }
    }

    public static boolean usernameValidation(String username) {
        return username.matches("^[a-zA-Z0-9._-]{3,}$");
    }


    static void showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Wait for the user to close the dialog
        alert.showAndWait();
    }

    public void handleRegisterHereClick() {
        mainApplication.showSignUpPage((Stage) registerHere.getScene().getWindow());
    }



}
