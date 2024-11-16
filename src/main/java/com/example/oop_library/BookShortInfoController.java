package com.example.oop_library;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookShortInfoController {

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookID;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Label catagory;

    @FXML
    private Label publicationYear;

    public void setData(Books b, DashboardController dashboardController) {
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        bookID.setText("ISBN:" + b.getBookID());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(getClass().getResourceAsStream(b.getThumbNail())));
    }

}
