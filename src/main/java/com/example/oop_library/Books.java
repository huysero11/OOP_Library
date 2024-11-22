package com.example.oop_library;

import java.time.LocalDate;

import java.util.*;

import com.google.gson.annotations.SerializedName;

public class Books {

    @SerializedName("imageLinks")
    protected Image image;
    static class Image {

        @SerializedName("thumbnail")
        private String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    @SerializedName("industryIdentifiers")
    private List<ISBN> isbn = new ArrayList<>();
    static class ISBN {
        @SerializedName("type")
        private String type;

        @SerializedName("identifier")
        private String identifier;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
    }

    @SerializedName("title")
    private String bookName;
    @SerializedName("authors")
    private List<String> authors = new ArrayList<>();   
    @SerializedName("publishedDate")
    private String bookPublicationYear;
    @SerializedName("categories")
    private List<String> categories = new ArrayList<>();
    @SerializedName("description")
    private String description;
    transient protected LocalDate borrowedDate;
    transient protected LocalDate returnDate;
    private boolean borrowed = false;
    private User borrowerInfo;

    public Books(String bookID, String bookName, String bookAuthor, String bookPublicationYear, String thumbNail,
            String catagory, String description, LocalDate borrowedDate, LocalDate returnDate, boolean borrowed,
            User borrowerInfo) { 
        ISBN isbn = new ISBN();
        isbn.setIdentifier(bookID);
        this.isbn.add(isbn);
        this.bookName = bookName;
        authors.add(bookAuthor);
        this.bookPublicationYear = bookPublicationYear;
        categories.add(catagory);
        this.description = description;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.borrowed = borrowed;
        this.borrowerInfo = borrowerInfo;
        image = new Image();
        image.setThumbnail(thumbNail);
    }


    public Books() {

    }

    public Books(String bookID, String bookName, String bookAuthor, String bookPublicationYear, String thumbNail, LocalDate borrowedDate,
            LocalDate returnDate) {
        this.bookName = bookName;
        authors.add(bookAuthor);
        this.bookPublicationYear = bookPublicationYear;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        image = new Image();
        ISBN isbn = new ISBN();
        isbn.setIdentifier(bookID);
        this.isbn.add(isbn);
        image.setThumbnail(thumbNail);
    }

    public String getBookID() {
        if (isbn != null && !isbn.isEmpty()) {
            return isbn.get(0).getIdentifier();
        }
        return "Unidentified";
    }

    public void setBookID(String bookID) {
        ISBN isbn = new ISBN();
        isbn.setIdentifier(bookID);
        this.isbn.add(isbn);
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        if (authors != null && !authors.isEmpty()) {
            return authors.get(0); 
        }
        return "Unknown Author"; 
    }

    public void setBookAuthor(List<String> bookAuthor) {
        this.authors = bookAuthor;
    }

    public String getBookPublicationYear() {
        return bookPublicationYear;
    }

    public void setBookPublicationYear(String bookPublicationYear) {
        this.bookPublicationYear = bookPublicationYear;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getThumbNail() {
        return image.getThumbnail();
    }

    public void setThumbNail(String thumbNail) {
        //this.thumbNail = thumbNail;
    }

    public String getCatagory() {
        if (categories != null && !categories.isEmpty()) {
            return categories.get(0); 
        }
        return "Unknown category"; 
    }

    public void setCatagory(List<String> catagory) {
        this.categories = catagory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public User getBorrowerInfo() {
        return borrowerInfo;
    }

    public void setBorrowerInfo(User borrowerInfo) {
        this.borrowerInfo = borrowerInfo;
    }

    public static List<Books> featuredBooks() {
        List<Books> listB = new ArrayList<>();
        listB = BooksDao.getInstance().getAll();
        return listB;
    }

}

