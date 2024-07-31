package org.shoppingappdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.shoppingappdemo.Database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNoField;
    @FXML
    private Button signUpButton;
    Main mainApplication;

    public void initialize() {
        signUpButton.setOnAction(event -> {
            try {
                handleSignUpButtonClick();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean saveUser(User user) throws ClassNotFoundException {
        Connection connection = Database.getConnection();
        try (connection) {
            String query = "INSERT INTO users (username, password, email, phoneNo) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;}

    private void handleSignUpButtonClick() throws ClassNotFoundException {
        // Get data from fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNo = phoneNoField.getText();

        // Create a new user instance
        User newUser = new User(username, password, email, phoneNo);


        boolean result = saveUser(newUser);
        if(result){
            showConfirmationDialog("User signed up successfully!");
            UserSession.setLoggedInUsername(username);
            mainApplication.showUserDashboard((Stage) signUpButton.getScene().getWindow());
        }
        else{
            showConfirmationDialog("User sign up failed!");
        }


    }
    private void showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Wait for the user to close the dialog
        alert.showAndWait();
    }

    public void setMainApplication(Main main) {
        this.mainApplication = main;
    }
}
