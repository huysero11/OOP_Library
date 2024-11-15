package com.example.oop_library;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String password;
    private boolean admin = false;
    private ArrayList<Books> borrowedBooks = new ArrayList<>();

    public User(int id, String name, String phoneNumber, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.admin = admin;
    }

    public User(int id, String name, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String name, String phoneNumber, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Books> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Books> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }


    public String toString() {
        return ("id = " + this.id + ", name = " + this.name
                + ", phoneNumber = " + this.phoneNumber
                + ", password = " + this.password
                + ", admin = " + this.admin
                );
    }
}
