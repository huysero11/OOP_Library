package com.example.oop_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {
    @FXML
    private TextField resetPasswordPhoneNumberTextField;

    @FXML
    private TextField resetPasswordNewPasswordPasswordField;

    @FXML
    private TextField resetPasswordConfirmPasswordPasswordField;

    @FXML
    private Label resetPasswordCheckingAccountLabel;

    public void resetPassword(ActionEvent event) {
        String phoneNumber = resetPasswordPhoneNumberTextField.getText();
        String newPassword = resetPasswordNewPasswordPasswordField.getText();
        String confirmPassword = resetPasswordConfirmPasswordPasswordField.getText();

        User user = UserDAO.getInstance().getByPhoneNumber(phoneNumber);
        if (user == null) {
            resetPasswordCheckingAccountLabel.setText("Invalid phone number!");
        } else {
            if (!newPassword.equals(confirmPassword)) {
                resetPasswordCheckingAccountLabel.setText("Passwords do not match!");
            } else {
                user.setPassword(newPassword);
                UserDAO.getInstance().update(user);
                resetPasswordCheckingAccountLabel.setText("Reset password successfully!");
                resetPasswordCheckingAccountLabel.setStyle("-fx-text-fill: green;");

                // go to main view
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
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
}
