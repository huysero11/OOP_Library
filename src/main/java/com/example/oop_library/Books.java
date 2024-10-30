package com.example.oop_library;

import java.util.*;

public class Books {

    private String bookID;
    private String bookName;
    private String bookAuthor;
    private String bookPublicationYear;
    private String thumbNail;
    private String catagory;
    private String description;
    private Date borrowedDate;
    private Date returnDate;
    public static List<Books> featuredBooksList = new ArrayList<>(featuredBooks());

    public Books() {

    }

    public Books(String bookID, String bookName, String bookAuthor, String bookPublicationYear, String thumbNail, Date borrowedDate,
            Date returnDate) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublicationYear = bookPublicationYear;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
        this.thumbNail = thumbNail;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublicationYear() {
        return bookPublicationYear;
    }

    public void setBookPublicationYear(String bookPublicationYear) {
        this.bookPublicationYear = bookPublicationYear;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static List<Books> featuredBooks() {
        List<Books> listB = new ArrayList<>();
        Books b1 = new Books("123", "Chí Phèo", 
                    "Nam Cao", null, 
                    "images/truyen-ngan-chi-pheo-nam-cao_17a42d45c5e84fdcb8a2365d91d4e2c6.jpg", null, null);
        Books b2 = new Books(null, "Hai đứa trẻ",   
                    "Thạch Lam", null, "images/2024_06_10_11_08_25_1-390x510.png", null, null);
        Books b3 = new Books(null,      
                    "Men&Women", "Someone", null, 
                    "images/81RfW9mFkEL._AC_UF1000,1000_QL80_.jpg", null, null);
        listB.add(b1);
        listB.add(b2);
        listB.add(b3);
        Books b4 = new Books(null,      
        "Men&Women", "Someone", null, 
        "images/81RfW9mFkEL._AC_UF1000,1000_QL80_.jpg", null, null);
        Books b5 = new Books(null,      
        "Men&Women", "Someone", null, 
        "images/81RfW9mFkEL._AC_UF1000,1000_QL80_.jpg", null, null);
        listB.add(b4);
        listB.add(b5);
        b1.setBookPublicationYear("1941");
        b1.setCatagory("Truyện ngắn");
        b1.setDescription("Chí Phèo là một truyện ngắn nổi tiếng của nhà văn Nam Cao viết vào tháng 2 năm 1941. Chí Phèo là một tác phẩm xuất sắc, thể hiện nghệ thuật viết truyện độc đáo của Nam Cao, đồng thời là một tấn bi kịch của một người nông dân nghèo bị tha hóa trong xã hội. Chí Phèo cũng là tên nhân vật chính của truyện.");
        return listB;
    }
    

}

