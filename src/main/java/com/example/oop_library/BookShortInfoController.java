package com.example.oop_library;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    private Books b;

    public void setData(Books b) {
        this.b = b;
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(b.getThumbNail()));
    }

    public void addBooks() {
        if( BooksDao.getInstance().add(b) == 1) {
            Platform.runLater(() -> {
                APISearchController.getSearchAPI().close();
            });
        }

    }
}
