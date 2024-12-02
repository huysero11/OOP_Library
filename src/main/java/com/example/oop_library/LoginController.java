package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginPhoneNumberTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Label loginCheckingAccountLabel;

    @FXML
    private StackPane loginStackPane;

    @FXML
    private ImageView loginBackgroundImageView;

    @FXML
    public void initialize() {
        loginStackPane.setPrefWidth(UseForAll.APP_PREF_WIDTH);
        loginStackPane.setPrefHeight(UseForAll.APP_PREF_HEIGHT);

        loginBackgroundImageView.setFitWidth(UseForAll.APP_PREF_WIDTH);
        loginBackgroundImageView.setFitHeight(UseForAll.APP_PREF_HEIGHT);

        // Add key press event to trigger login on "Enter" key
        loginPhoneNumberTextField.setOnKeyPressed(this::handleEnterKey);
        loginPasswordField.setOnKeyPressed(this::handleEnterKey);
    }

    // Trigger login when Enter is pressed
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login(new ActionEvent(event.getSource(), null));
        }
    }

    @FXML
    public void login(ActionEvent event) {
        String phoneNumber = loginPhoneNumberTextField.getText();
        String password = loginPasswordField.getText();

        if (phoneNumber.isEmpty() || password.isEmpty()) {
            loginCheckingAccountLabel.setText("Please fill in both fields!");
            loginCheckingAccountLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        User user = UserDAO.getInstance().getByPhoneNumber(phoneNumber);
        if (user == null) {
            loginCheckingAccountLabel.setText("Your phone number is not correct or your account does not exist!");
            loginCheckingAccountLabel.setStyle("-fx-text-fill: red;");
            loginPhoneNumberTextField.clear();
            loginPasswordField.clear();
        } else {
            if (!user.getPassword().equals(password)) {
                loginCheckingAccountLabel.setText("Your password is incorrect!");
                loginCheckingAccountLabel.setStyle("-fx-text-fill: red;");
                loginPasswordField.clear();
            } else {
                loginCheckingAccountLabel.setText("Logged in Successfully!");
                loginCheckingAccountLabel.setStyle("-fx-text-fill: green;");

                loginPhoneNumberTextField.clear();
                loginPasswordField.clear();

                // Save user's info
                SessionManager.getInstance().startSession(user);

                // Navigate to user's dashboard
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/FXML/DashBoard.fxml"));
                    Parent root = fxmlLoader.load();

                    // Get the current stage from the event
                    DashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setLoggedInUser(user);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Login Error: Could not load dashboard.");
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void openSignUpView(MouseEvent event) {
        navigateToView(event, "/com/example/oop_library/FXML/SignUpView.fxml");
    }

    @FXML
    public void openResetPasswordView(MouseEvent event) {
        navigateToView(event, "/com/example/oop_library/FXML/ResetPasswordView.fxml");
    }

    private void navigateToView(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
//            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            System.out.println("Navigation Error: Could not load " + fxmlPath);
            e.printStackTrace();
        }
    }
}
