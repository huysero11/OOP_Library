package com.example.oop_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsController {
    @FXML
    private VBox centerArea;

    @FXML
    private Label username;

    @FXML
    private Label phoneNumber;

    @FXML
    private Button backButton;

    @FXML
    private TableView<BorrowedBook> tableView;

    @FXML
    private TableColumn<BorrowedBook, Integer> idColumn;

    @FXML
    private TableColumn<BorrowedBook, ImageView> book;

    @FXML
    private TableColumn<BorrowedBook, String> name;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> borrowDate;

    @FXML
    private TableColumn<BorrowedBook, LocalDate> returnDate;

    @FXML
    private ObservableList<BorrowedBook> bookList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        book.setCellValueFactory(new PropertyValueFactory<>("book"));
        book.setCellFactory(column -> new TableCell<BorrowedBook, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }


    public void setData(User user, UserController userController) {
        username.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        loadBookData(user);
    }

    public void loadBookData(User user) {
        String link = "http://books.google.com/books/content?id=1YwtPwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api";
        Image image = new Image(link);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(70);

        String name = "Harry Potter and the Half-Blood Prince";
        bookList.add(new BorrowedBook(1, imageView, name, LocalDate.now(), LocalDate.now().plusDays(7)));
        tableView.setItems(bookList);
    }

    public void handleBackButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User.fxml"));
            VBox p = fxmlLoader.load();

            UserController userController = fxmlLoader.getController();

            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
