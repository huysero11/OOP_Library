package com.example.oop_library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AdminController {

    @FXML
    private VBox adminVBox;

    @FXML
    public void initialize() {
        adminVBox.setPrefWidth(UseForAll.BORDERPANE_CENTER_PREF_WIDTH);
        adminVBox.setPrefHeight(UseForAll.BORDERPANE_CENTER_PREF_HEIGHT);
    }

    public void OpenSearchAPIBooksStage() {
        APISearchController apiSearchController = new APISearchController();
        apiSearchController.CreateSearchBooksStage();
    }

    public void OpenDeleteStage() {
        DeleteBooksController deleteBooksController = new DeleteBooksController();
        deleteBooksController.CreateSearchBooksStage("Delete");
    }

    public void OpenUpdateStage() {
        DeleteBooksController deleteBooksController = new DeleteBooksController();
        deleteBooksController.CreateSearchBooksStage("Update");
    }
}

