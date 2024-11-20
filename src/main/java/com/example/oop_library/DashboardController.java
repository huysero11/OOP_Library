package com.example.oop_library;

import java.util.*;

import java.net.URL;

import javafx.application.Platform;
import javafx.beans.binding.NumberExpression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable{

    @FXML
    private VBox centerArea;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        switchToDashBoard();
    }


    public void switchToDashBoard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Home.fxml"));
            VBox p = fxmlLoader.load();
            HomeController homeController = fxmlLoader.getController();
            homeController.showFeaturedBooks(this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AdminView.fxml"));
            VBox p = fxmlLoader.load();
            // bookDetailController detailController = fxmlLoader.getController();
            // detailController.setData(b, this);
            // AdminController adminController = fxmlLoader.getController();
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToBookDetail(Books b) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("bookDetails.fxml"));
            VBox p = fxmlLoader.load();
            bookDetailController detailController = fxmlLoader.getController();
            detailController.setData(b, this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
