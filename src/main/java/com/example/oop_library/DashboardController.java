package com.example.oop_library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable {

    @FXML
    private GridPane featuredBooks;

    @FXML
    private VBox centerArea;

    private VBox oldCenterArea = new VBox();

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            showFeaturedBooks();
        });

        showFeaturedBooks();
    }

    public void showFeaturedBooks() {
        double scrollPaneWidth = scrollPane.getWidth();
        featuredBooks.getChildren().clear();

        // Create a task to load books in a background thread
        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int column = 0;
                int row = 1;

                for (int i = 0; i < Books.featuredBooksList.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                        VBox card = fxmlLoader.load();
                        bookCardController cardController = fxmlLoader.getController();
                        cardController.setData(Books.featuredBooksList.get(i), DashboardController.this);

                        // Add the card to the UI using Platform.runLater
                        int finalColumn = column;
                        int finalRow = row;
                        Platform.runLater(() -> {
                            featuredBooks.add(card, finalColumn, finalRow);
                            GridPane.setMargin(card, new Insets(2));
                        });

                        column++;
                        if (column == (int) (scrollPaneWidth / (card.getPrefWidth() + 2))) {
                            column = 0;
                            ++row;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        // Handle task success or failure
        loadBooksTask.setOnFailed(event -> {
            Throwable exception = loadBooksTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        // Run the task in a separate thread
        Thread loadThread = new Thread(loadBooksTask);
        loadThread.setDaemon(true); // Mark as a daemon thread to close when the app exits
        loadThread.start();
    }

    public void switchToBookDetail(Books b) {
        oldCenterArea.getChildren().clear();
        oldCenterArea.getChildren().addAll(centerArea.getChildren());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("bookDetails.fxml"));
            VBox p = fxmlLoader.load();
            bookDetailController detailController = fxmlLoader.getController();
            detailController.setData(b, this);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToDashBoard() {
        centerArea.getChildren().clear();
        centerArea.getChildren().addAll(oldCenterArea.getChildren());
    }

    public void switchToAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AdminView.fxml"));
            VBox p = fxmlLoader.load();
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
