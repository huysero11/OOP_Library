package com.example.oop_library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.NumberExpression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

    @FXML
    private VBox centerArea;

    @FXML
    private BorderPane dashboardBorderPane;

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        switchToDashBoard();

        dashboardBorderPane.setPrefWidth(UseForAll.APP_PREF_WIDTH);
        dashboardBorderPane.setPrefHeight(UseForAll.APP_PREF_HEIGHT);
    }


    public void switchToDashBoard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/Home.fxml"));
            VBox p = fxmlLoader.load();
            p.getStylesheets().add(getClass().getResource("/com/example/oop_library/CSS/ContextMenu.css").toExternalForm());
            HomeController homeController = fxmlLoader.getController();
            homeController.setLoggedInUser(loggedInUser);
            homeController.showFeaturedBooks(this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);

            TransitionUtils.applyFadeTransition(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/AdminView.fxml"));
            VBox p = fxmlLoader.load();
            // bookDetailController detailController = fxmlLoader.getController();
            // detailController.setData(b, this);
            // AdminController adminController = fxmlLoader.getController();
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);

            TransitionUtils.applyFadeTransition(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToBookDetail(Books b) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/bookDetails.fxml"));
            VBox p = fxmlLoader.load();
            bookDetailController detailController = fxmlLoader.getController();
            detailController.setData(b, this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);

            TransitionUtils.applyFadeTransition(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToBorrowedBooks() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/BorrowedBooks.fxml"));
            VBox p = fxmlLoader.load();
            BorrowedBooksController borrowedBooksController = fxmlLoader.getController();
            borrowedBooksController.showBorrowedBooks();
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);

            TransitionUtils.applyFadeTransition(p);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
