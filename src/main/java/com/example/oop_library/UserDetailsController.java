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
        idColumn.setCellFactory(column -> new TableCell<BorrowedBook, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        book.setCellValueFactory(new PropertyValueFactory<>("book"));
        book.setCellFactory(column -> new TableCell<BorrowedBook, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                    setStyle("-fx-alignment: CENTER;"); // Căn giữa
                }
            }
        });

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(column -> new TableCell<BorrowedBook, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        // Cột Ngày mượn (căn giữa)
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        borrowDate.setCellFactory(column -> new TableCell<BorrowedBook, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnDate.setCellFactory(column -> new TableCell<BorrowedBook, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
    }



    public void setData(User user, UserController userController) {
        username.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        loadBookData(user);
    }

    public void loadBookData(User user) {
        try {
            Connection connection = MySQLConnection.getConnection();
            String query = "SELECT thumbnail, book_name, borrowed_date, return_date FROM books WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id++;
                String thumbnail = resultSet.getString("thumbnail");
                Image image = new Image(thumbnail);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(70);
                String bookName = resultSet.getString("book_name");
                LocalDate borrowedDate = resultSet.getDate("borrowed_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date").toLocalDate();
                bookList.add(new BorrowedBook(id, imageView, bookName, borrowedDate, returnDate));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
