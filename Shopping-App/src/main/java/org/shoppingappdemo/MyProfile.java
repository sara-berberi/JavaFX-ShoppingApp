package org.shoppingappdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MyProfile {

    @FXML
    private AnchorPane totalPane;
    @FXML
    private Button logOutButton;

    @FXML
    private GridPane ProfileDataContainer;

    private final Label usernameLabel = new Label();
    private final Label emailLabel = new Label();
    private final Label phoneLabel = new Label();

    private Main mainApplication;

    public void initialize() {
        logOutButton.setOnAction(event -> handleLogOutButtonClick());
    }

    public void populateWithUserData() {
        String loggedInUser = UserSession.getLoggedInUsername();
        User user = User.getUser(loggedInUser);

        usernameLabel.setText("Username: " + user.getUsername());
        emailLabel.setText("Email: " + user.getEmail());
        phoneLabel.setText("Phone: " + user.getPhone());

        modifyDataContainer();
    }

    public static String getLoggedInUser() {
        return UserSession.getLoggedInUsername();
    }

    public void handleLogOutButtonClick() {
        mainApplication.showLoginPage(mainApplication.getPrimaryStage());
    }

    public void setMainApplication(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void modifyDataContainer() {

        if(ProfileDataContainer == null) {
            System.out.println("ProfileDataContainer is null");
        }
        else {
            // Clear existing content in ProfileDataContainer
            ProfileDataContainer.getChildren().clear();

            // Add labels to ProfileDataContainer
            ProfileDataContainer.add(usernameLabel, 0, 0);
            ProfileDataContainer.add(emailLabel, 0, 1);
            ProfileDataContainer.add(phoneLabel, 0, 2);
        }
    }

    public AnchorPane getTotalPane() {
        return totalPane;
    }
}
