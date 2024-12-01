package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

public class SupportController {

    private HomeController homeController;

    @FXML
    private VBox centerArea;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    public void switchToSupport(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/Support.fxml"));
            VBox supportView = fxmlLoader.load();

            BorderPane mainPane = (BorderPane) centerArea.getParent();
            mainPane.setCenter(supportView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
