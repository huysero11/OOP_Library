package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginPhoneNumberTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginCheckingAccountLabel;


    public void login(ActionEvent event) {
        String phoneNumber = loginPhoneNumberTextField.getText();
        String password = loginPasswordField.getText();

        User user = UserDAO.getInstance().getByPhoneNumber(phoneNumber);
        if (user == null) {
            loginCheckingAccountLabel.setText("Your phone number is not correct or your account does not exist!");
            loginPhoneNumberTextField.clear();
            loginPasswordField.clear();
        } else {
            if (!user.getPassword().equals(password)) {
                loginCheckingAccountLabel.setText("Your password is incorrect!");
                loginPasswordField.clear();
            } else {
                loginCheckingAccountLabel.setText("Logged in Successfully!");
                loginCheckingAccountLabel.setStyle("-fx-text-fill: green;");


                loginPhoneNumberTextField.clear();
                loginPasswordField.clear();

                // Save user's info.
                SessionManager.getInstance().startSession(user);

                // login to user's account
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/app.fxml"));
                    Parent root = fxmlLoader.load();

//                    MainMenuController mainMenuController = fxmlLoader.getController();
//                    mainMenuController.setLoggedInUser(user);

                    // get the current stage from the event
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
//                    scene.getStylesheets().add(getClass().getResource("ContextMenu.css").toExternalForm());;
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Login Error");
                    e.printStackTrace();
                }
            }
        }
    }

    public void openSignUpView(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/SignUpView.fxml"));
            Parent root = fxmlLoader.load();

            // get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openResetPasswordView(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/ResetPasswordView.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}