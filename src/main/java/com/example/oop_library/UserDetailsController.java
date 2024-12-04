package com.example.oop_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
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
    private Button updateButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private ProgressIndicator loadingSpinner;

    @FXML
    private static Stage updateUser = new Stage();

    private User user;

    private Object previousController;

    public void setPreviousController(Object previousController) {
        this.previousController = previousController;
    }

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setUpdateButtonVisible(boolean visible) {
        updateButton.setVisible(visible);
    }

    private ObservableList<BorrowedBook> bookList = FXCollections.observableArrayList();

    private Label placeholderLabel = new Label("This user has not borrowed any book yet.");

    @FXML
    public void initialize() {
        tableView.setPlaceholder(new Label(""));
        centerArea.setPrefWidth(UseForAll.BORDERPANE_CENTER_PREF_WIDTH);
        centerArea.setPrefHeight(UseForAll.BORDERPANE_CENTER_PREF_HEIGHT);

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

    public void setData(User user) {
        username.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        this.user = user;
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
                    tableView.setPlaceholder(new Label(""));
                    loadingSpinner.setVisible(false);
                    if (bookList.isEmpty()) {
                        tableView.setPlaceholder(placeholderLabel);
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    loadingSpinner.setVisible(false);
                });
            }
        }).start();
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            if (previousController instanceof AdminController) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/FXML/AdminView.fxml"));
                VBox adminView = fxmlLoader.load();
                AdminController adminController = fxmlLoader.getController();
                centerArea.getChildren().clear();
                centerArea.getChildren().add(adminView);
            } else if (previousController instanceof HomeController) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/FXML/Home.fxml"));
                VBox homeView = fxmlLoader.load();
                HomeController homeController = fxmlLoader.getController();
                homeController.setLoggedInUser(user);
                homeController.showFeaturedBooks(dashboardController);
                centerArea.getChildren().clear();
                centerArea.getChildren().add(homeView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateButtonAction() {
        try {
            tableView.getItems().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oop_library/FXML/UpdateUser.fxml"));
            Parent root = loader.load();
            UpdateUserController updateUserController = loader.getController();

            updateUserController.setUser(user);
            updateUserController.setUserDetailsController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update User");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteAccountButtonAction() {
        UserDAO.getInstance().delete(user);
        handleBackButtonAction();
    }

    public void setDeleteAccountButtonVisible(boolean visible) {
        deleteAccountButton.setVisible(visible);
    }
}
