package com.example.oop_library;

import java.util.*;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable{

    @FXML
    private HBox featuredBooks;

    @FXML
    private VBox centerArea;

    private VBox oldCenterArea = new VBox();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            for (int i = 0; i < Books.featuredBooksList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                VBox card = fxmlLoader.load();
                bookCardController cardController = fxmlLoader.getController();
                cardController.setData(Books.featuredBooksList.get(i), this);
                featuredBooks.getChildren().add(card);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void switchToBookDetail(Books b) {
        oldCenterArea.getChildren().clear();
        oldCenterArea.getChildren().addAll(centerArea.getChildren());
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

    public void switchToDashBoard() {
        centerArea.getChildren().clear();
        centerArea.getChildren().addAll(oldCenterArea.getChildren());
    }
}
