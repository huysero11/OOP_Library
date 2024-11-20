package com.example.oop_library;

public class AdminController {

    public void OpenSearchAPIBooksStage() {
        APISearchController apiSearchController = new APISearchController();
        apiSearchController.CreateSearchBooksStage();
    }

    public void OpenDeleteStage() {
        DeleteBooksController deleteBooksController = new DeleteBooksController();
        deleteBooksController.CreateSearchBooksStage();
    }
}

