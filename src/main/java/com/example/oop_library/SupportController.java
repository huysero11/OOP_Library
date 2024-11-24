package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Support.fxml")); 
            VBox supportView = fxmlLoader.load();

            centerArea.getChildren().clear();
            centerArea.getChildren().add(supportView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
