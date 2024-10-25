package com.example.oop_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ResetPasswordController {
    @FXML
    private TextField resetPasswordPhoneNumberTextField;

    @FXML
    private TextField resetPasswordNewPasswordPasswordField;

    @FXML
    private TextField resetPasswordConfirmNewPasswordPasswordField;

    @FXML
    private Label resetPasswordCheckingAccountLabel;

    public void resetPassword(ActionEvent event) {
        String phoneNumber = resetPasswordPhoneNumberTextField.getText();
        String newPassword = resetPasswordNewPasswordPasswordField.getText();
        String confirmPassword = resetPasswordConfirmNewPasswordPasswordField.getText();

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

}
