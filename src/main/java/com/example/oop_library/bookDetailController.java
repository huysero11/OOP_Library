

package com.example.oop_library;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class bookDetailController {
    @FXML
    private Button backButton;

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookID;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private ToggleButton borrowButton;

    @FXML
    private Label catagory;

    @FXML
    private Label description;

    @FXML
    private Label publicationYear;

    private DashboardController dashboardController;

    private Books b;

    public void setData(Books b, DashboardController dashboardController) {
        this.b = b;
        this.dashboardController = dashboardController;
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        bookID.setText("ISBN: " + b.getBookID());
        this.description.setText(b.getDescription());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(b.getThumbNail()));
        if (b.isBorrowed()) {
            borrowButton.setText("BORROWED");
            borrowButton.getStyleClass().clear();
            borrowButton.getStyleClass().add("borrowed-button");
        }
    }

    public void handleBackButtonAction() {
        dashboardController.switchToDashBoard();
    }

    public void setBorrowButton() {
        startStatusUpdateThread();
        if (borrowButton.getText().equals("BORROW")) {
            BookBorrowTransaction bookBorrowTransaction = new BookBorrowTransaction(); 
            System.out.println(bookBorrowTransaction.borrowBooks(b, SessionManager.getInstance().getCurrentUser()));
        }
    }

    // Start a new thread to continuously update book status (e.g. checking if it's borrowed)
    private void startStatusUpdateThread() {
        Task<Void> statusUpdateTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Loop to periodically check the book status
                while (true) {
                    // Simulate getting new status of the book from a database or external source
                    Thread.sleep(500);  // Sleep for 5 seconds before checking again

                    // Here, we simulate updating the book's borrowed status
                    // You can replace this with an actual call to update status from your backend
                    if (b.isBorrowed()) {
                        // If borrowed, keep updating button as "BORROWED"
                        Platform.runLater(() -> {
                            borrowButton.setText("BORROWED");
                            borrowButton.getStyleClass().clear();
                            borrowButton.getStyleClass().add("borrowed-button");
                        });
                        break;
                    }
                }
                return null;
            }
        };

        // Start the task in a separate thread
        new Thread(statusUpdateTask).start();
    }
}
