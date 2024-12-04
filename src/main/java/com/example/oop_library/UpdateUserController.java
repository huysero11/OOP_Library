package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateUserController {
    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField newUserPassword;

    @FXML
    private TextField newUserPhoneNumber;

    @FXML
    private PasswordField confirmNewUserPassword;

    @FXML
    private Button confirmButton;

    @FXML
    private Label passwordCheckingLabel;

    @FXML
    private Label phoneNumberCheckingLabel;

    private User user;

    private UserDetailsController userDetailsController;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserDetailsController(UserDetailsController userDetailsController) {
        this.userDetailsController = userDetailsController;
    }

    @FXML
    public void handleConfirmButtonAction() {
        String newName;
        String newPassword = user.getPassword();
        String newPhoneNumber = user.getPhoneNumber();
        boolean admin = user.isAdmin();
        int id = user.getId();
        boolean hasError = false;

        newName = (newUsername.getText() != null && !newUsername.getText().trim().isEmpty())
                ? newUsername.getText() : user.getName();

        if (newUserPhoneNumber.getText() != null && !newUserPhoneNumber.getText().trim().isEmpty()) {
            String phoneNumberRegex = "^\\d+$";
            if (!newUserPhoneNumber.getText().matches(phoneNumberRegex)) {
                phoneNumberCheckingLabel.setText("Phone number must only contain digits 0-9!");
                phoneNumberCheckingLabel.setVisible(true); // Hiển thị cảnh báo
                hasError = true;
            } else {
                phoneNumberCheckingLabel.setVisible(false); // Ẩn cảnh báo nếu hợp lệ
                newPhoneNumber = newUserPhoneNumber.getText();
            }
        }

        if (newUserPassword.getText() != null && !newUserPassword.getText().trim().isEmpty()) {
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
            if (!newUserPassword.getText().matches(passwordRegex)) {
                passwordCheckingLabel.setText("Password must have a minimum length of 8, contain uppercase, lowercase, digits, and special characters.");
                passwordCheckingLabel.setVisible(true);
                hasError = true;
            } else if (confirmNewUserPassword.getText() == null) {
                passwordCheckingLabel.setText("Confirm password must not be empty!");
                passwordCheckingLabel.setVisible(true);
                hasError = true;
            } else if (!newUserPassword.getText().equals(confirmNewUserPassword.getText())) {
                passwordCheckingLabel.setText("Passwords do not match!");
                passwordCheckingLabel.setVisible(true);
                hasError = true;
            } else {
                passwordCheckingLabel.setVisible(false);
                newPassword = newUserPassword.getText();
            }
        }

        if (hasError) {
            return;
        }

        User newUser = new User(id, newName, newPhoneNumber, newPassword, admin);
        int res = UserDAO.getInstance().update(newUser);
        userDetailsController.setData(newUser);

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }


}
