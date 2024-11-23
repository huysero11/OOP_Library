package com.example.oop_library;

import javafx.application.Platform;

public class AdminController {

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

