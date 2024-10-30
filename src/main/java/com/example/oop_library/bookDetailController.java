

package com.example.oop_library;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class bookDetailController {
    @FXML
    private Button backButton;

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookID;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Button borrowButton;

    @FXML
    private Label catagory;

    @FXML
    private Label description;

    @FXML
    private Label publicationYear;

    private DashboardController dashboardController;

    public void setData(Books b, DashboardController dashboardController) {
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        bookID.setText("ISBN:" + b.getBookID());
        this.description.setText(b.getDescription());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(getClass().getResourceAsStream(b.getThumbNail())));
        this.dashboardController = dashboardController;
    }

    public void handleBackButtonAction() {
        if (dashboardController != null)
            dashboardController.switchToDashBoard();
    }
}
