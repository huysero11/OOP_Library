package com.example.oop_library;

import java.time.LocalDate;

public class BookBorrowTransaction {
    
    public String borrowBooks(Books books, User user) {
        // if (this.getStatus() == AccountStatus.Banned) {
        //     System.out.println("You are banned.");
        //     System.out.println("Please settle the fine to continue using the service!");
        if (user.getBorrowedBooks().size() == 5) {
            return "You can only borrow up to 5 books!\n Return a book before borrowing more.";
        } else if (books.isBorrowed()) {
            return "This book is already borrowed by another user!";
        } else {     
            if (books.isBorrowed() == false) {
                // Cập nhật trạng thái sách
                books.setBorrowedDate(LocalDate.now());
                books.setReturnDate(LocalDate.now().plusMonths(1)); // Thời gian cho mượn một cuốn sách tối đa 1 tháng.
                user.getBorrowedBooks().add(books); // Phát sách cho người mượn.
                books.setBorrowed(true); // Thay đổi trạng thái của cuốn sách.
                books.setBorrowerInfo(user); 
                // Cập nhật database books. 
                BooksDao.getInstance().update(books);
            }
        }
        if (user.getBorrowedBooks().indexOf(books) >= 0 && books.isBorrowed() == true) {
            System.out.println("Completed!");
        } 
        return String.format("Success! Please remember to return this book by %s", books.getReturnDate());
    }
}