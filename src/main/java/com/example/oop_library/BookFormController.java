package com.example.oop_library;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookFormController {

    @FXML
    private TextField authorField;

    @FXML
    private TextField borrowDateField;

    @FXML
    private TextField borrowerIDField;

    @FXML
    private Label formName;

    @FXML
    private TextField genreField;

    @FXML
    private TextField imageURLField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField publicationDateField;

    @FXML
    private TextField returnDateField;

    @FXML
    private Button submitButton;

    @FXML
    private TextArea summaryField;

    @FXML
    private TextField titleField;

    @FXML
    private VBox bookFormVBox;

    private Books b;

    private BookFormController bookFormController;

    private static Stage updateBookFormStage = new Stage();

    @FXML
    public void initialize() {
        bookFormVBox.setPrefWidth(UseForAll.APP_PREF_WIDTH - 30);
        bookFormVBox.setPrefHeight(UseForAll.APP_PREF_HEIGHT - 30);
    }

    public TextField getAuthorField() {
        return authorField;
    }

    public void setAuthorField(TextField authorField) {
        this.authorField = authorField;
    }

    public TextField getBorrowDateField() {
        return borrowDateField;
    }

    public void setBorrowDateField(TextField borrowDateField) {
        this.borrowDateField = borrowDateField;
    }

    @SuppressWarnings("exports")
    public TextField getBorrowerIDField() {
        return this.borrowerIDField;
    }

    public void setBorrowerIDField(TextField borrowerIDField) {
        this.borrowerIDField = borrowerIDField;
    }

    public Label getFormName() {
        return formName;
    }

    public void setFormName(Label formName) {
        this.formName = formName;
    }

    public TextField getGenreField() {
        return genreField;
    }

    public void setGenreField(TextField genreField) {
        this.genreField = genreField;
    }

    public TextField getImageURLField() {
        return imageURLField;
    }

    public void setImageURLField(TextField imageURLField) {
        this.imageURLField = imageURLField;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public void setIsbnField(TextField isbnField) {
        this.isbnField = isbnField;
    }

    public TextField getPublicationDateField() {
        return publicationDateField;
    }

    public void setPublicationDateField(TextField publicationDateField) {
        this.publicationDateField = publicationDateField;
    }

    public TextField getReturnDateField() {
        return returnDateField;
    }

    public void setReturnDateField(TextField returnDateField) {
        this.returnDateField = returnDateField;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(Button submitButton) {
        this.submitButton = submitButton;
    }

    public TextArea getSummaryField() {
        return summaryField;
    }

    public void setSummaryField(TextArea summaryField) {
        this.summaryField = summaryField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public void setTitleField(TextField titleField) {
        this.titleField = titleField;
    }

    public static Stage getSearchAPI() {
        return updateBookFormStage;
    }

    public static void setUpdateBookFormStage(Stage updateBookFormStage) {
        BookFormController.updateBookFormStage = updateBookFormStage;
    }

    public BookFormController getBookFormController() {
        return bookFormController;
    }

    public void setBookFormController(BookFormController bookFormController) {
        this.bookFormController = bookFormController;
    }

    public void CreateUpdateBooksFormStage(Books b) {

        //searchAPI.initStyle(StageStyle.TRANSPARENT);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/oop_library/FXML/BookForm.fxml"));
            Parent root = fxmlLoader.load();
            BookFormController bookFormController = fxmlLoader.getController();
            this.bookFormController = bookFormController;
            bookFormController.setData(b);
            updateBookFormStage.setScene(new Scene(root, 880, 618));
            updateBookFormStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Books b) {
        this.b = b;
        titleField.setText(b.getBookName());
        authorField.setText(b.getBookAuthor());
        genreField.setText(b.getCatagory());
        summaryField.setText(b.getDescription());
        borrowerIDField.setText(b.getBorrowerInfo() != null ? Integer.toString(b.getBorrowerInfo().getId()) : "");
        borrowDateField.setText(b.getBorrowedDate() != null ? b.getBorrowedDate().toString() : "");
        returnDateField.setText(b.getReturnDate() != null ? b.getReturnDate().toString() : "");
        publicationDateField.setText(b.getBookPublicationYear());
        isbnField.setText(b.getBookID());
        imageURLField.setText(b.getThumbNail());
    }

    public void setSubmitButton() {
        b.setBookName(titleField.getText());
        b.setBookAuthor(Arrays.asList(authorField.getText()));
        b.setCatagory(Arrays.asList(genreField.getText()));
        b.setDescription(summaryField.getText());
        b.setBookID(isbnField.getText());
        boolean status;
        User user;
        if (borrowerIDField.getText() != null && !borrowerIDField.getText().isEmpty()) {
            user = UserDAO.getInstance().getById(Integer.parseInt(borrowerIDField.getText()));
            status = true;
            System.out.println(user.getId());
        } else { user = null; status = false;}
        b.setBorrowerInfo(user);
        b.setThumbNail(imageURLField.getText());
        b.setBookPublicationYear(publicationDateField.getText());
        if (!borrowDateField.getText().isEmpty()) {
            b.setBorrowedDate(LocalDate.parse(borrowDateField.getText()));
        }
        else {
            b.setBorrowedDate(null);
        }
        if (!returnDateField.getText().isEmpty()) {
            if (LocalDate.parse(returnDateField.getText()).compareTo(LocalDate.now().plusMonths(1)) > 0) {
                returnDateField.setText(LocalDate.now().plusMonths(1).toString());
            }
            b.setReturnDate(LocalDate.parse(returnDateField.getText()));
        } else {
            b.setReturnDate(null);
        }
        
        b.setBorrowed(status);
        if (BooksDao.getInstance().update(b) == 1) {
            System.out.println("Updated!");
            updateBookFormStage.close();
        }
    }

    public void Edit() {
        titleField.setEditable(false);
        authorField.setEditable(false);
        genreField.setEditable(false);
        summaryField.setEditable(false);
        publicationDateField.setEditable(false);
        isbnField.setEditable(false);
        imageURLField.setEditable(false);
        titleField.setOpacity(0.5);
        authorField.setOpacity(0.5);
        genreField.setOpacity(0.5);
        summaryField.setOpacity(0.5);
        publicationDateField.setOpacity(0.5);
        isbnField.setOpacity(0.5);
        imageURLField.setOpacity(0.5);
    }

    
    public void Edit1() {
        titleField.setEditable(false);
        authorField.setEditable(false);
        genreField.setEditable(false);
        summaryField.setEditable(false);
        publicationDateField.setEditable(false);
        isbnField.setEditable(false);
        imageURLField.setEditable(false);
        titleField.setOpacity(0.5);
        authorField.setOpacity(0.5);
        genreField.setOpacity(0.5);
        summaryField.setOpacity(0.5);
        publicationDateField.setOpacity(0.5);
        isbnField.setOpacity(0.5);
        imageURLField.setOpacity(0.5);
        borrowerIDField.setEditable(false);
        borrowDateField.setEditable(false);
        returnDateField.setEditable(false);
        borrowerIDField.setOpacity(0.5);
        borrowDateField.setOpacity(0.5);
        returnDateField.setOpacity(0.5);
    }
}
