package com.example.oop_library;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminController {
    @FXML
    ProgressIndicator loadingIndicator;

    @FXML 
    Button searchButton;

    public void OpenSearchAPIBooksStage() {
        Stage searchAPI = new Stage();
        searchAPI.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("APISearchView.fxml"));
            searchAPI.setScene(new Scene(root, 680, 208));
            searchAPI.show();
            Platform.runLater(() -> loadingIndicator.setVisible(false));
            searchButton.setOnAction(event -> {
                Platform.runLater(() -> loadingIndicator.setVisible(true));

                new Thread(() -> {
                    try {
                        Thread.sleep(3000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> loadingIndicator.setVisible(false));
                }).start();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
