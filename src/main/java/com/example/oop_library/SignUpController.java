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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField signUpNameTextField;

    @FXML
    private TextField signUpPhoneNumberTextField;

    @FXML
    private PasswordField signUpPasswordField;

    @FXML
    private PasswordField signUpConfirmPasswordPasswordField;

    @FXML
    private Label signUpCheckingAccountLabel;

    @FXML
    private StackPane signUpStackPane;

    @FXML
    public void initialize() {
        signUpStackPane.setPrefWidth(UseForAll.APP_PREF_WIDTH);
        signUpStackPane.setPrefHeight(UseForAll.APP_PREF_HEIGHT);
    }

    public void signUp(ActionEvent event) {
        String name = signUpNameTextField.getText();
        String phoneNumber = signUpPhoneNumberTextField.getText();
        String password = signUpPasswordField.getText();
        String confirmPassword = signUpConfirmPasswordPasswordField.getText();

        String phoneNumberRegex = "^\\d+$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        /*
            ^ : asserts the start of the string.
            (?=.*[a-z]) :  ensures at least one lowercase letter.
            (?=.*[A-Z]) :  ensures at least one uppercase letter.
            (?=.*\\d) :  ensures at least one digit.
            (?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]) :  ensures at least one special character (you can modify the set of special characters as needed).
            .{8,} :  ensures that the password is at least 8 characters long.
            $ :  asserts the end of the string.
         */
        User user = UserDAO.getInstance().getByPhoneNumber(phoneNumber);
        if (user != null) {
            signUpCheckingAccountLabel.setText("This phone number has been used!");
        } else {
            if (!phoneNumber.matches(phoneNumberRegex)) {
                signUpCheckingAccountLabel.setText("Phone number only contains digits 0-9!");
            } else {
                if (!password.matches(passwordRegex)) {
                    signUpCheckingAccountLabel.setText("Your password should have the minimum length of 8 "
                            + "and contain lowercase, uppercase, digits, and special characters!");
                } else if (!password.equals(confirmPassword)) {
                    signUpCheckingAccountLabel.setText("Passwords do not match!");
                } else {
                    user = new User(name, phoneNumber, password);
                    UserDAO.getInstance().add(user);
                    signUpCheckingAccountLabel.setText("Signed up successfully!");
                    signUpCheckingAccountLabel.setStyle("-fx-text-fill: green;");

                    // open main view
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/MainView.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        signUpCheckingAccountLabel.setText("Error loading the main view.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void openLoginView(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/LoginView.fxml"));
            Parent root = fxmlLoader.load();
            // get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
//            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
