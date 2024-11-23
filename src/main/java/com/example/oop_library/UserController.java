package com.example.oop_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        // Thiết lập CellValueFactory cho các cột
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        loadUserDataFromDatabase();
        addButtonToTable();
    }

    private void loadUserDataFromDatabase() {
        String query = "SELECT user_id, user_name, user_phone FROM users";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String phoneNumber = resultSet.getString("user_phone");

                userList.add(new User(id, name, phoneNumber));
            }

            // Đưa dữ liệu từ ObservableList vào TableView
            userTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi kết nối Database
        }
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
                        //btn.setStyle("-fx-background-color: #027b39; -fx-text-fill: white;");
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
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void handleBackButtonAction() {
        dashboardController.switchToDashBoard();
    }

}
