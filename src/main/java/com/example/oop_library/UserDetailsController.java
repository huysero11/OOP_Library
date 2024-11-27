package com.example.oop_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.sql.*;
import java.time.LocalDate;

public class UserDetailsController {

    @FXML
    private VBox centerArea;

    @FXML
    private Label username;

    @FXML
    private Label phoneNumber;

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
    private ProgressIndicator loadingSpinner;

    private ObservableList<BorrowedBook> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tableView.setPlaceholder(new Label(""));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        book.setCellValueFactory(new PropertyValueFactory<>("book"));
        book.setCellFactory(new Callback<TableColumn<BorrowedBook, ImageView>, TableCell<BorrowedBook, ImageView>>() {
            @Override
            public TableCell<BorrowedBook, ImageView> call(TableColumn<BorrowedBook, ImageView> param) {
                return new TableCell<BorrowedBook, ImageView>() {
                    @Override
                    protected void updateItem(ImageView item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(item);
                        }
                    }
                };
            }
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        loadingSpinner.setVisible(false);
    }

    public void setData(User user, UserController userController) {
        username.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        loadBookData(user);
    }

    public void loadBookData(User user) {
        loadingSpinner.setVisible(true);

        new Thread(() -> {
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
                    ImageView imageView = new ImageView(thumbnail);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(70);
                    String bookName = resultSet.getString("book_name");
                    LocalDate borrowedDate = resultSet.getDate("borrowed_date").toLocalDate();
                    LocalDate returnDate = resultSet.getDate("return_date").toLocalDate();
                    bookList.add(new BorrowedBook(id, imageView, bookName, borrowedDate, returnDate));
                }

                javafx.application.Platform.runLater(() -> {
                    tableView.setItems(bookList);
                    loadingSpinner.setVisible(false);
                });

            } catch (SQLException e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    loadingSpinner.setVisible(false);
                });
            }
        }).start();
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
