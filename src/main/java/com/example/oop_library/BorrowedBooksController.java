package com.example.oop_library;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import java.util.List;

public class BorrowedBooksController {

    @FXML
    private ListView<HBox> borrowedBooksListView;  

    private static ObservableList<HBox> borrowedBooks = FXCollections.observableArrayList();

    public void showBorrowedBooks() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        System.out.println(currentUser.getId());
        List<Books> listBorrowedBooks = BooksDao.getInstance().getByCondition("user_id = " + currentUser.getId());
        borrowedBooks.clear();
        for (Books b : listBorrowedBooks) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BorrowedBooksInfo.fxml"));
                HBox hBox = fxmlLoader.load();  
                BorrowedBooksInfoController borrowedBooksInfoController = fxmlLoader.getController();
                borrowedBooksInfoController.setData(b);  
                hBox.setUserData(borrowedBooksInfoController);
                borrowedBooks.add(hBox);

            } catch (Exception e) {
                e.printStackTrace();  
            }
        }
        borrowedBooksListView.setItems(borrowedBooks);
    }

    public static void removeBookFromObservableList(BorrowedBooksInfoController control) {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i).getUserData().equals(control)) borrowedBooks.remove(i);   
        }
    }
}
