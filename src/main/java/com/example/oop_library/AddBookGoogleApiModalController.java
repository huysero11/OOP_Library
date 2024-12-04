package com.example.oop_library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressIndicator;

import java.util.List;

public class AddBookGoogleApiModalController {

    @FXML
    private TextField titleField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox foundBooksContainer;
    @FXML
    private Label errorLabel;
    @FXML
    private ProgressIndicator loadingIndicator;

    private GoogleBooksService gbsService = new GoogleBooksService();

    public void initialize() {
        errorLabel.setVisible(false);
        searchButton.setOnAction(event -> onSearch());
    }

    private void onSearch() {
        String title = titleField.getText();
        if (title.isEmpty()) {
            errorLabel.setText("Please enter a book title.");
            errorLabel.setVisible(true);
            return;
        }

        loadingIndicator.setVisible(true);
        errorLabel.setVisible(false);

        new Thread(() -> {
            GBSDto results = gbsService.searchGBS(title);
            System.out.println(results);
            List<GBSBooks> l1 = results.getItems();
            System.out.println(l1.size());
            for (GBSBooks b : l1) {
                System.out.println(b.getVolumeInfo().getBookName());
            }
            loadingIndicator.setVisible(false);
            Platform.runLater(() -> showFoundBooks(results != null ? results.getItems() : null));
        }).start();
    }

    private void showFoundBooks(List<GBSBooks> foundBooks) {
        Platform.runLater(() -> {
            foundBooksContainer.getChildren().clear();

            if (foundBooks == null || foundBooks.isEmpty()) {
                errorLabel.setText("No books found.");
                errorLabel.setVisible(true);
                return;
            }

            // for (GBSBooks book : foundBooks) {
            //     Button bookButton = new Button(book.getVolumeInfo().getBookName());
            //     bookButton.setOnAction(event -> showBookDetails(book));
            //     foundBooksContainer.getChildren().add(bookButton);
            // }
        });
    }

    private void showBookDetails(GBSBooks book) {
        Books volumeInfo = book.getVolumeInfo();
        System.out.println("Selected book: " + volumeInfo.getBookName());
        System.out.println("ID: " + volumeInfo.getBookID());
        System.out.println("Author: " + volumeInfo.getBookAuthor());
        System.out.println("Thumbnail: " + volumeInfo.getThumbNail());
        System.out.println("Category: " + volumeInfo.getCatagory());
        System.out.println("ISBN: " + volumeInfo.getBookID());
        System.out.println("Description: " + volumeInfo.getDescription());
    }
}
