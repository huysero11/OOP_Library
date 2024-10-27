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
    private PasswordField signUpConfirmPasswordField;

    @FXML
    private Label signUpCheckingAccountLabel;


    public void signUp(ActionEvent event) {
        String name = signUpNameTextField.getText();
        String phoneNumber = signUpPhoneNumberTextField.getText();
        String password = signUpPasswordField.getText();
        String confirmPassword = signUpConfirmPasswordField.getText();

        String phoneNumberRegex = "^\\d+$";
        String passwordRegex = "^[a-zA-Z0-9]+$";

        User user = UserDAO.getInstance().getByPhoneNumber(phoneNumber);
        if (user != null) {
            signUpCheckingAccountLabel.setText("This phone number has been used!");
        } else {
            if (!phoneNumber.matches(phoneNumberRegex)) {
                signUpCheckingAccountLabel.setText("Phone number only contains digits 0-9!");
            } else {
                if (!password.matches(passwordRegex)) {
                    signUpCheckingAccountLabel.setText("Password only contains characters a-z or A-Z or numbers 0-9!");
                } else if (!password.equals(confirmPassword)) {
                    signUpCheckingAccountLabel.setText("Passwords do not match!");
                } else {
                    UserDAO.getInstance().add(user);
                    signUpCheckingAccountLabel.setText("Signed up successfully!");
                    signUpCheckingAccountLabel.setStyle("-fx-text-fill: green;");

                    // open main view

                }
            }

        }
    }


    public void backToLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/LoginView.fxml"));
            Parent root = fxmlLoader.load();

            // get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
