package com.example.oop_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.*;

public class UserController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> phoneNumberColumn;

    @FXML
    private TableColumn<User, Void> detailsColumn;

    @FXML
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private VBox centerArea;

    @FXML
    private Button backButton;

    private DashboardController dashboardController;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        loadUserDataFromDatabase();
        addButtonToTable();
    }

    private void loadUserDataFromDatabase() {
        Task<ObservableList<User>> loadDataTask = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() throws Exception {
                ObservableList<User> tempList = FXCollections.observableArrayList();
                String query = "SELECT user_id, user_name, user_phone FROM users";

                try (Connection connection = MySQLConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query);
                     ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        int id = resultSet.getInt("user_id");
                        String name = resultSet.getString("user_name");
                        String phoneNumber = resultSet.getString("user_phone");
                        tempList.add(new User(id, name, phoneNumber));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return tempList;
            }
        };

        loadDataTask.setOnSucceeded(event -> {
            userList.clear();
            userList.addAll(loadDataTask.getValue());
            userTable.setItems(userList);
        });

        loadDataTask.setOnFailed(event -> {
            Throwable exception = loadDataTask.getException();
            showErrorDialog("Error loading users", exception.getMessage());
        });

        Thread loadThread = new Thread(loadDataTask);
        loadThread.setDaemon(true);
        loadThread.start();
    }

    private void addButtonToTable() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            showDetails(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        detailsColumn.setCellFactory(cellFactory);
    }

    private void showDetails(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("UserDetails.fxml"));
            VBox p = fxmlLoader.load();
            UserDetailsController detailController = fxmlLoader.getController();
            detailController.setData(user, this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleBackButtonAction() {
        dashboardController.switchToDashBoard();
    }
}
