package com.example.oop_library;

import java.io.IOException;

import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class APISearchController {
    @FXML
    private Label errorLabel;

    @FXML
    private VBox foundBooks;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Button searchButton;

    @FXML
    private TextField titleField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox apiSearchVBox;

    private GoogleBooksService gsbService = new GoogleBooksService();

    private static Stage searchAPI = new Stage();

    public static Stage getSearchAPI() {
        return searchAPI;
    }

    public static void setSearchAPI(Stage searchAPI) {
        APISearchController.searchAPI = searchAPI;
    }

    @FXML
    public void initialize() {
        apiSearchVBox.setPrefWidth(UseForAll.APP_PREF_WIDTH);
        apiSearchVBox.setPrefHeight(UseForAll.APP_PREF_HEIGHT);
    }

    public void CreateSearchBooksStage() {

        //searchAPI.initStyle(StageStyle.TRANSPARENT);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("APISearchView.fxml"));
            searchAPI.setScene(new Scene(root, 880, 618));
            searchAPI.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSearch() {
        String title = titleField.getText();
        if (title.isEmpty()) {
            errorLabel.setText("Please enter a book title");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        loadingIndicator.setVisible(true);
        foundBooks.getChildren().clear();


        new Thread(() -> {
            try {
                GBSDto results = gsbService.searchGBS(title);
                List<GBSBooks> listBooks = results.getItems();
                if (results.getTotalItems() == 0) {
                    errorLabel.setText("Please enter a book title");
                    errorLabel.setVisible(true);
                    return;
                }
                Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    scrollPane.setVisible(true);
                    showFoundBooks(listBooks);
                });
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }).start();
    }

    public void showFoundBooks(List<GBSBooks> listBooks) {
        for (GBSBooks b : listBooks) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("bookShortInfo.fxml"));
                HBox p = fxmlLoader.load();
                BookShortInfoController bookShortInfoController = fxmlLoader.getController();
                bookShortInfoController.setData(b.getVolumeInfo());
                foundBooks.getChildren().add(p);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}


