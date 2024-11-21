package com.example.oop_library;

import java.time.LocalDate;

public class BorrowedBook extends Books {
    private int borrowedBookID;

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

    public int getId() {
        return borrowedBookID;
    }

    public void setId(int borrowedBookID) {
        this.borrowedBookID = borrowedBookID;
    }

    public Image getBook() {
        return image;
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
