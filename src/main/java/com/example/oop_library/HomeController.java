package com.example.oop_library;

import java.lang.classfile.Label;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class HomeController {
    @FXML
    private GridPane featuredBooks;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private MenuButton searchBy;

    @FXML
    private MenuItem searchByAuthor;

    @FXML
    private MenuItem searchByCategory;

    @FXML
    private MenuItem searchByTiTle;

    @FXML
    private TextField searchQuery;

    private String searchCriteria = "book_name like ";

    DashboardController dashboardController;

    // Chưa dùng Thread
    public void showFeaturedBooks(DashboardController dashboardController) {
        setSearchCriteria();
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scrollPaneWidth = scrollPane.getWidth();
            featuredBooks.getChildren().clear();

            int column = 0;
            int row = 1;
            try {
                List<Books> featuredBooksList = Books.featuredBooks();
                int s = featuredBooksList.size();
                for (int i = 0; i < s; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                    VBox card = fxmlLoader.load();
                    bookCardController cardController = fxmlLoader.getController();
                    cardController.setData(featuredBooksList.get(i), dashboardController);
                    featuredBooks.add(card, column, row);
                    GridPane.setMargin(card, new Insets(1));
                    column++;
                    System.out.println(scrollPaneWidth + " " + scrollPaneWidth/(card.getPrefWidth() + 2));
                    if (column == (int) (scrollPaneWidth/(card.getPrefWidth() + 2))) {
                        column = 0;
                        ++row;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        });
        this.dashboardController = dashboardController;
    }

    // public void showFeaturedBooks() {
    //     double scrollPaneWidth = scrollPane.getWidth();
    //     featuredBooks.getChildren().clear();

    //     // Create a task to load books in a background thread
    //     Task<Void> loadBooksTask = new Task<>() {
    //         @Override
    //         protected Void call() throws Exception {
    //             int column = 0;
    //             int row = 1;

    //             for (int i = 0; i < Books.featuredBooksList.size(); i++) {
    //                 try {
    //                     FXMLLoader fxmlLoader = new FXMLLoader();
    //                     fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
    //                     VBox card = fxmlLoader.load();
    //                     bookCardController cardController = fxmlLoader.getController();
    //                     cardController.setData(Books.featuredBooksList.get(i), DashboardController.this);

    //                     // Add the card to the UI using Platform.runLater
    //                     int finalColumn = column;
    //                     int finalRow = row;
    //                     Platform.runLater(() -> {
    //                         featuredBooks.add(card, finalColumn, finalRow);
    //                         GridPane.setMargin(card, new Insets(2));
    //                     });

    //                     column++;
    //                     if (column == (int) (scrollPaneWidth / (card.getPrefWidth() + 2))) {
    //                         column = 0;
    //                         ++row;
    //                     }
    //                 } catch (Exception e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //             return null;
    //         }
    //     };

    //     // Handle task success or failure
    //     loadBooksTask.setOnFailed(event -> {
    //         Throwable exception = loadBooksTask.getException();
    //         if (exception != null) {
    //             exception.printStackTrace();
    //         }
    //     });

    //     // Run the task in a separate thread
    //     Thread loadThread = new Thread(loadBooksTask);
    //     loadThread.setDaemon(true); // Mark as a daemon thread to close when the app exits
    //     loadThread.start();
    // }

    public void searchBooks() {
        searchQuery.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!searchQuery.getText().isEmpty() && searchQuery.getText() != null)
                    performSearch(searchCriteria, searchQuery.getText());
            }
        });
    }

    public void setSearchCriteria() {
        searchByTiTle.setOnAction(event -> {
            searchBy.setText("By Title");
            searchCriteria = "book_name like";
        });
        searchByAuthor.setOnAction(event -> {
            searchBy.setText("By Author");
            searchCriteria = "book_author like ";
        });
        searchByCategory.setOnAction(event -> {
            searchBy.setText("By Category");
            searchCriteria = "catagory like ";
        });
    }

    public void performSearch(String searchCriteria, String searchQuery) {
        System.out.println(searchCriteria + " " + String.format("'%%%s%%'", searchQuery.trim()));
        ArrayList<Books> list = BooksDao.getInstance().getByCondition(searchCriteria + String.format("'%%%s%%'", searchQuery.trim()));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getBookName());
        }

        featuredBooks.getChildren().clear();
            double scrollPaneWidth = scrollPane.getWidth();
            featuredBooks.getChildren().clear();

            int column = 0;
            int row = 1;
            try {
                ArrayList<Books> featuredBooksList = list;
                int s = featuredBooksList.size();
                for (int i = 0; i < s; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                    VBox card = fxmlLoader.load();
                    bookCardController cardController = fxmlLoader.getController();
                    cardController.setData(featuredBooksList.get(i), dashboardController);
                    featuredBooks.add(card, column, row);
                    GridPane.setMargin(card, new Insets(1));
                    column++;
                    System.out.println(scrollPaneWidth + " " + scrollPaneWidth/(card.getPrefWidth() + 2));
                    if (column == (int) (scrollPaneWidth/(card.getPrefWidth() + 2))) {
                        column = 0;
                        ++row;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
    }
}


