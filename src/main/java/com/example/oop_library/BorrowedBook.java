package com.example.oop_library;

import javafx.scene.image.ImageView;
import java.time.LocalDate;

public class BorrowedBook extends Books {
    private int borrowedBookID;

    private ImageView bookImage;

    public BorrowedBook(int borrowedBookID, Image thumbnail, LocalDate borrowedDate, LocalDate returnedDate) {
        this.borrowedBookID = borrowedBookID;
        this.image = thumbnail;
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnedDate);
    }

    public BorrowedBook(int borrowedBookID, Image thumbnail, String name, LocalDate borrowedDate, LocalDate returnedDate) {
        this.borrowedBookID = borrowedBookID;
        this.image = thumbnail;
        this.setBookName(name);
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnedDate);
    }

    public BorrowedBook(int borrowedBookID, ImageView thumbnail, String name, LocalDate borrowedDate, LocalDate returnedDate) {
        this.borrowedBookID = borrowedBookID;
        this.bookImage = thumbnail;
        this.setBookName(name);
        this.setBorrowedDate(borrowedDate);
        this.setReturnDate(returnedDate);
    }


    public ImageView getBook() {
        return bookImage;
    }

    public void setBook(ImageView book) {
        this.bookImage = book;
    }


    public int getId() {
        return borrowedBookID;
    }

    public void setId(int borrowedBookID) {
        this.borrowedBookID = borrowedBookID;
    }
    

    public void setBook(Image book) {
        this.image = book;
    }

    public String getName() {
        return this.getBookName();
    }

    public LocalDate getBorrowDate() {
        return getBorrowedDate();
    }

    public LocalDate getReturnDate() {
        return super.getReturnDate();
    }
}
