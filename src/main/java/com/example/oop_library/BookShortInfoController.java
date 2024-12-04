package com.example.oop_library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookShortInfoController {

    @FXML
    private Label bookAuthor;

    @FXML
    private Label bookID;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    @FXML
    private Label catagory;

    @FXML
    private Label publicationYear;

    private Books b;

    @FXML
    private Button selectBooks;

    public void setData(Books b) {
        this.b = b;
        bookName.setText(b.getBookName());
        bookAuthor.setText(b.getBookAuthor());
        this.catagory.setText(b.getCatagory());
        this.publicationYear.setText("Publication Year: " + b.getBookPublicationYear());
        bookImage.setImage(new Image(b.getThumbNail()));
    }

    public void addBooks() {
            int row = BooksDao.getInstance().add(b);
            if(row == 1) {
                Platform.runLater(() -> {
                    APISearchController.getSearchAPI().close();
                });
                try {
                    Stage notifStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/oop_library/FXML/Notification.fxml")));
                    Parent root = fxmlLoader.load();
                    NotificationController notificationController = fxmlLoader.getController();
                    //notificationController.setImageNotif("/com/example/oop_library/images/remove.png");
                    notificationController.setTextNotif("Add Book Succesfully");
                    notifStage.setScene(new Scene(root));
                    notifStage.show();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else {
                try {
                    Stage notifStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/oop_library/FXML/Notification.fxml")));
                    Parent root = fxmlLoader.load();
                    NotificationController notificationController = fxmlLoader.getController();
                    notificationController.setImageNotif("/com/example/oop_library/images/remove.png");
                    notificationController.setTextNotif("Fail to add new book\n Duplicated entry!");
                    notifStage.setScene(new Scene(root));
                    notifStage.show();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
    }

    public void setSelectBooks(String operation) {
        selectBooks.setOnAction(null);
        if (operation.equals("Delete")) {
            selectBooks.setOnMouseClicked((MouseEvent event) -> {
                if (BooksDao.getInstance().delete(b) == 1) {
                    DeleteBooksController.getSearchAPI().close();
                    try {
                        Stage notifStage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/oop_library/FXML/Notification.fxml")));
                        Parent root = fxmlLoader.load();
                        NotificationController notificationController = fxmlLoader.getController();
                        //notificationController.setImageNotif("/com/example/oop_library/images/remove.png");
                        notificationController.setTextNotif("Delete Succesfully");
                        notifStage.setScene(new Scene(root));
                        notifStage.show();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    System.out.println("Deleted!");
                } else {
                    try {
                        Stage notifStage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/com/example/oop_library/FXML/Notification.fxml")));
                        Parent root = fxmlLoader.load();
                        NotificationController notificationController = fxmlLoader.getController();
                        notificationController.setImageNotif("/com/example/oop_library/images/remove.png");
                        notificationController.setTextNotif("Cannot delete this book!");
                        notifStage.setScene(new Scene(root));
                        notifStage.show();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
        } else if (operation.equals("Update")) {
            selectBooks.setOnMouseClicked((MouseEvent event) -> {
                BookFormController bookFormController = new BookFormController();
                bookFormController.CreateUpdateBooksFormStage(b);
            });
        }
    }

}
