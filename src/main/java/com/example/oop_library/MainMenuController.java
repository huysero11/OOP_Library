package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class MainMenuController {
    @FXML
    private ContextMenu avaMenu;

    @FXML
    private void initialize() {
        // Tạo và cấu hình ContextMenu nếu nó không được định nghĩa trong FXML
        if (avaMenu == null) {
            avaMenu = new ContextMenu();
            MenuItem profile = new MenuItem("Profile");
            MenuItem logOut = new MenuItem("Log out");
            MenuItem about = new MenuItem("About");
            avaMenu.getItems().addAll(profile, logOut, about);
        }
    }

    @FXML
    public void avatarMenu(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            Node source = (Node) event.getSource();
            avaMenu.show(source, event.getScreenX(), event.getScreenY());
        }
    }
}
