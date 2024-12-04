package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateUserController {
    @FXML
    private TextField newUsername;

    @FXML
    private TextField newUserPassword;

    @FXML
    private TextField newUserPhoneNumber;

    @FXML
    private Button confirmButton;

    @FXML
    private Label passwordCheckingLabel;

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
        String newPhoneNumber;
        boolean admin = user.isAdmin();
        int id = user.getId();
        newName = (newUsername.getText() != null && !newUsername.getText().trim().isEmpty())
                ? newUsername.getText() : user.getName();
        newPhoneNumber = (newUserPhoneNumber.getText() != null && !newUserPhoneNumber.getText().trim().isEmpty())
                ? newUserPhoneNumber.getText() : user.getPhoneNumber();

        if (newUserPassword.getText() != null && !newUserPassword.getText().trim().isEmpty()) {
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
            while (!newUserPassword.getText().matches(passwordRegex)) {
                passwordCheckingLabel.setText("Your password should have the minimum length of 8 "
                        + "and contain lowercase, uppercase, digits, and special characters!");
            }
            newPassword = newUserPassword.getText();
        }
        User newUser = new User(id, newName, newPhoneNumber, newPassword, admin);
        int res = UserDAO.getInstance().update(newUser);
        userDetailsController.setData(newUser);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
