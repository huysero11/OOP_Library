package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainMenuController {
    private User loggedInUser;

    @FXML
    private ImageView avatarImageView;

    private ContextMenu contextMenu;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @FXML
    public void initialize() {
        // Tạo ContextMenu và các MenuItem
        contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("context-menu");

        MenuItem profile = new MenuItem("Profile");
        MenuItem logOut = new MenuItem("Log Out");
        MenuItem borrowedBook = new MenuItem("Borrowed Book");
        MenuItem deleteAccount = new MenuItem("Delete Account");

        contextMenu.getItems().addAll(profile, borrowedBook, logOut, deleteAccount);

        avatarImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(avatarImageView, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

        logOut.setOnAction(e -> logOutUser());
        deleteAccount.setOnAction(e -> deleteUserAccount());
    }

    private void logOutUser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/LoginView.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) avatarImageView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserAccount() {
        try {
            Connection connection = MySQLConnection.getConnection();
            String query = "DELETE FROM users WHERE user_phone = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, loggedInUser.getPhoneNumber());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            logOutUser();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
