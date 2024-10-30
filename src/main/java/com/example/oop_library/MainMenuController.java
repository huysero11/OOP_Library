package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MainMenuController {
    @FXML
    private ImageView avatarImageView;

    private ContextMenu contextMenu;

    @FXML
    public void initialize() {
        // Tạo ContextMenu và các MenuItem
        contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("context-menu");

        MenuItem profile = new MenuItem("Profile");
        MenuItem logOut = new MenuItem("Log Out");
        MenuItem borrowedBook = new MenuItem("Borrowed Book");
        MenuItem deleteAccount = new MenuItem("Delete Account");

        // Thêm MenuItem vào ContextMenu
        contextMenu.getItems().addAll(profile, logOut, borrowedBook, deleteAccount);

        // Thiết lập sự kiện nhấn chuột phải trên avatarImageView
        avatarImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(avatarImageView, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }
}
