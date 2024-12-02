package com.example.oop_library;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @FXML
    private Pane centerArea;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private ContextMenu contextMenu;

    private String searchCriteria = "book_name like ";

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    DashboardController dashboardController;

    @FXML
    public void initialize() {
        centerArea.setPrefWidth(UseForAll.BORDERPANE_CENTER_PREF_WIDTH);
        centerArea.setPrefHeight(UseForAll.BORDERPANE_CENTER_PREF_HEIGHT);

        contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("context-menu");

        MenuItem profile = new MenuItem("Profile");
        MenuItem deleteAccount = new MenuItem("Delete Account");

        contextMenu.getItems().addAll(profile, deleteAccount);

        avatarImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(avatarImageView, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

        profile.setOnAction(e -> switchToProfile());
        deleteAccount.setOnAction(e -> deleteUserAccount());
    }

    public void showFeaturedBooks(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
        setSearchCriteria();

        // Ensure dynamic resizing of GridPane
        featuredBooks.setMinHeight(Region.USE_COMPUTED_SIZE);
        featuredBooks.setPrefHeight(Region.USE_COMPUTED_SIZE);
        featuredBooks.setMaxHeight(Region.USE_PREF_SIZE);

        // Configure ScrollPane to allow vertical scrolling only
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Disable horizontal scroll
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Enable vertical scroll

        // Force layout update after the first load
        Platform.runLater(() -> {
            featuredBooks.requestLayout(); // Recalculate the GridPane layout
            scrollPane.requestLayout();    // Recalculate the ScrollPane layout
        });

        // Use a thread pool for parallel loading
        ExecutorService executor = Executors.newFixedThreadPool(4); // Adjust thread pool size as needed

        Task<Void> loadBooksTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<Books> featuredBooksList = Books.featuredBooks();
                double scrollPaneWidth = scrollPane.getPrefWidth();

                AtomicInteger column = new AtomicInteger(0);
                AtomicInteger row = new AtomicInteger(1);

                for (Books book : featuredBooksList) {
                    if (isCancelled()) break;

                    executor.submit(() -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/bCard.fxml"));
                            VBox card = fxmlLoader.load();
                            bookCardController cardController = fxmlLoader.getController();
                            cardController.setData(book, dashboardController);

                            Platform.runLater(() -> {
                                featuredBooks.add(card, column.get(), row.get());
                                GridPane.setMargin(card, new Insets(1));

                                column.incrementAndGet();
                                System.out.println(scrollPaneWidth + " " + (scrollPaneWidth / card.getPrefWidth() + 2));
                                if (column.get() == (int) (scrollPaneWidth / (card.getPrefWidth() + 2))) {
                                    column.set(0);
                                    row.incrementAndGet();

                                    // Adjust the GridPane height dynamically
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
                fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/bCard.fxml"));
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

                        // Adjust the GridPane height dynamically
                        featuredBooks.setPrefHeight(row.get() * card.getPrefHeight() + 20);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToSupport() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/Support.fxml"));
            VBox supportView = fxmlLoader.load();

            SupportController supportController = fxmlLoader.getController();
            supportController.setHomeController(this);

            centerArea.getChildren().clear();
            centerArea.getChildren().add(supportView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToSignout(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/oop_library/FXML/LoginView.fxml"));
            Parent root = fxmlLoader.load();

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Login Error: Could not go to login view!");
            e.printStackTrace();
        }
    }

    public void switchToProfile() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/UserDetails.fxml"));
            VBox p = fxmlLoader.load();
            UserDetailsController detailController = fxmlLoader.getController();
            detailController.setData(loggedInUser);
            centerArea.getChildren().clear();
            centerArea.getChildren().add(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUserAccount() {
        try {
            Connection connection = MySQLConnection.getConnection();
            String query = "DELETE FROM users WHERE user_phone = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, loggedInUser.getPhoneNumber());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/example/oop_library/FXML/LoginView.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) avatarImageView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
