package com.example.oop_library;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteBooksController {

    @FXML
    private Label errorLabel;

    @FXML
    private VBox foundBooks;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button searchButton;

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
    private Label stageName;

    @FXML
    private VBox deleteBooksVBox;

    private static Stage searchDelete = new Stage();

    private String searchCriteria = "book_name like ";

    @FXML
    public void initialize() {
        deleteBooksVBox.setPrefWidth(UseForAll.APP_PREF_WIDTH);
        deleteBooksVBox.setPrefHeight(UseForAll.APP_PREF_HEIGHT);
    }

    public static Stage getSearchAPI() {
        return searchDelete;
    }

    public static void setSearchAPI(Stage searchDelete) {
        DeleteBooksController.searchDelete = searchDelete;
    }

    public void CreateSearchBooksStage(String stageName) {

        //searchAPI.initStyle(StageStyle.TRANSPARENT);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("DeleteBooksView.fxml"));
            Parent root = fxmlLoader.load();
            searchDelete.setScene(new Scene(root, 880, 618));
            searchDelete.show();
            DeleteBooksController deleteBooksController = fxmlLoader.getController();
            deleteBooksController.setSearchCriteria();
            deleteBooksController.getStageName().setText(stageName + " Available Books");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            searchCriteria = "catagory like ";
        });
    }

    public void performSearch(String searchCriteria, String searchQuery) {
        System.out.println(searchCriteria + " " + String.format("'%%%s%%'", searchQuery.trim()));
        ArrayList<Books> list = BooksDao.getInstance().getByCondition(searchCriteria + String.format("'%%%s%%'", searchQuery.trim()));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getBookName());
        }

        String input = stageName.getText();
        String[] words = input.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";

        System.out.println(firstWord);

        foundBooks.getChildren().clear();
        for (Books b : list) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("bookShortInfo.fxml"));
                HBox p = fxmlLoader.load();
                BookShortInfoController bookShortInfoController = fxmlLoader.getController();
                bookShortInfoController.setData(b);
                bookShortInfoController.setSelectBooks(firstWord);
                foundBooks.getChildren().add(p);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public Label getStageName() {
        return stageName;
    }

    public void setStageName(Label stageName) {
        this.stageName = stageName;
    }
}