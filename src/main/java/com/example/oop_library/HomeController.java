package com.example.oop_library;

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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void showFeaturedBooks(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
        setSearchCriteria();

        // Ensure dynamic resizing of GridPane
        featuredBooks.setMinHeight(Region.USE_COMPUTED_SIZE);
        featuredBooks.setPrefHeight(Region.USE_COMPUTED_SIZE);
        featuredBooks.setMaxHeight(Region.USE_PREF_SIZE);

        // Set ScrollPane properties
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true); // Enables dragging to scroll

        // Use a thread pool for parallel loading
        ExecutorService executor = Executors.newFixedThreadPool(4); // Adjust thread pool size as needed

        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<Books> featuredBooksList = Books.featuredBooks();
                double scrollPaneWidth = scrollPane.getWidth();

                AtomicInteger column = new AtomicInteger(0);
                AtomicInteger row = new AtomicInteger(1);

                for (Books book : featuredBooksList) {
                    if (isCancelled()) break;

                    executor.submit(() -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                            VBox card = fxmlLoader.load();
                            bookCardController cardController = fxmlLoader.getController();
                            cardController.setData(book, dashboardController);

                            Platform.runLater(() -> {
                                featuredBooks.add(card, column.get(), row.get());
                                GridPane.setMargin(card, new Insets(1));

                                column.incrementAndGet();
                                if (column.get() == (int) (scrollPaneWidth / (card.getPrefWidth() + 2))) {
                                    column.set(0);
                                    row.incrementAndGet();

                                    // Ensure dynamic height adjustment
                                    featuredBooks.setPrefHeight(row.get() * card.getPrefHeight() + 20);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                executor.shutdown();
                return null;
            }
        };

        loadBooksTask.setOnFailed(event -> {
            Throwable exception = loadBooksTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        Thread loadThread = new Thread(loadBooksTask);
        loadThread.setDaemon(true); // Ensures the thread stops when the application exits
        loadThread.start();
    }

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
            searchCriteria = "category like ";
        });
    }

    public void performSearch(String searchCriteria, String searchQuery) {
        System.out.println(searchCriteria + " " + String.format("'%%%s%%'", searchQuery.trim()));
        List<Books> list = BooksDao.getInstance().getByCondition(searchCriteria + String.format("'%%%s%%'", searchQuery.trim()));
        featuredBooks.getChildren().clear();
        double scrollPaneWidth = scrollPane.getWidth();

        AtomicInteger column = new AtomicInteger(0);
        AtomicInteger row = new AtomicInteger(1);

        try {
            for (Books book : list) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("bCard.fxml"));
                VBox card = fxmlLoader.load();
                bookCardController cardController = fxmlLoader.getController();
                cardController.setData(book, dashboardController);

                Platform.runLater(() -> {
                    featuredBooks.add(card, column.get(), row.get());
                    GridPane.setMargin(card, new Insets(1));

                    column.incrementAndGet();
                    if (column.get() == (int) (scrollPaneWidth / (card.getPrefWidth() + 2))) {
                        column.set(0);
                        row.incrementAndGet();

                        // Ensure dynamic height adjustment
                        featuredBooks.setPrefHeight(row.get() * card.getPrefHeight() + 20);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
