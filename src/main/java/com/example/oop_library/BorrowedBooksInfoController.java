package com.example.oop_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BorrowedBooksInfoController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Button borrowDetailButton;

    @FXML
    private Label catagory;

    @FXML
    private Label publicationYear;

    @FXML
    private ToggleButton returnBookButton;

    @FXML
    private HBox borrowedBooksInfoHBox;

    private Books b;

    @FXML
    public void initialize() {
        borrowedBooksInfoHBox.setPrefWidth(700);
    }

    public void setData(Books b) {
        this.b = b;
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(b.getThumbNail()));
    }

    public void setBorrowDetailButton() {
        BookFormController bookFormController = new BookFormController();
        bookFormController.CreateUpdateBooksFormStage(b);
        bookFormController.getBookFormController().Edit1();
        bookFormController.getBookFormController().getSubmitButton().setText("Success");
        bookFormController.getBookFormController().getSubmitButton().setStyle("-fx-background-color: transparent;");
        bookFormController.getBookFormController().getSubmitButton().setOnAction(null);
    }

    public void setReturnBookButton() {
        b.setBorrowerInfo(null);
        b.setBorrowedDate(null);
        b.setReturnDate(null);

        b.setBorrowed(false);

        if (BooksDao.getInstance().update(b) == 1) {
            System.out.println("Success");
        }

        BorrowedBooksController.removeBookFromObservableList(this);
    }
} 
