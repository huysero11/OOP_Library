package com.example.oop_library;

import static com.example.oop_library.APISearchController.searchAPI;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DeleteBooksController extends APISearchController {

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

    public void CreateSearchBooksStage() {

        //searchAPI.initStyle(StageStyle.TRANSPARENT);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("DeleteSearchView.fxml"));
            searchAPI.setScene(new Scene(root, 880, 618));
            searchAPI.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void searchBooks(KeyEvent event) {

    }

    
    
}
