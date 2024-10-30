package com.example.oop_library;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class bookCardController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private VBox bookCard;

    private Books b;

    private DashboardController dashboardController;

    public void setData(Books b, DashboardController dashboardController) {
        Image image = new Image(getClass().getResourceAsStream(b.getThumbNail()));
        System.out.println(image.getUrl());
        bookImage.setImage(image);
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.dashboardController = dashboardController;
        this.b = b;
    }

    @FXML 
    private void handleMouseEnter() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), bookCard);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.play();
    }

    @FXML 
    private void handleMouseExit() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), bookCard);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {

        dashboardController.switchToBookDetail(b);
    }
}
