package com.example.oop_library;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.NumberExpression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        if (!SessionManager.getInstance().getCurrentUser().isAdmin()) {
            try {
                    Stage notifStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/oop_library/FXML/Notification.fxml")));
                    Parent root = fxmlLoader.load();
                    NotificationController notificationController = fxmlLoader.getController();
                    notificationController.setImageNotif("/com/example/oop_library/images/remove.png");
                    notificationController.setTextNotif("This session is only available to Admin accounts!");
                    notifStage.setScene(new Scene(root));
                    notifStage.show();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/AdminView.fxml"));
                VBox p = fxmlLoader.load();
                // bookDetailController detailController = fxmlLoader.getController();
                // detailController.setData(b, this);
                AdminController adminController = fxmlLoader.getController();
                adminController.setUser(loggedInUser);
                centerArea.getChildren().clear();
                centerArea.getChildren().add(p);
                TransitionUtils.applyFadeTransition(p);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
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
