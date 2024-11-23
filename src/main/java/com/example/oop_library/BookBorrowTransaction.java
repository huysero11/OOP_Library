package com.example.oop_library;

import java.time.LocalDate;

public class BookBorrowTransaction {
    
    public String borrowBooks(Books b, User user) {
        // if (this.getStatus() == AccountStatus.Banned) {
        //     System.out.println("You are banned.");
        //     System.out.println("Please settle the fine to continue using the service!");
        if (user.getBorrowedBooks().size() == 5) {
            return "You can only borrow up to 5 books!\n Return a book before borrowing more.";
        } else if (b.isBorrowed()) {
            return "This book is already borrowed by another user!";
        } else {     
            if (b.isBorrowed() == false) {
                // Cập nhật trạng thái sách
                BookFormController bookFormController = new BookFormController();
                bookFormController.CreateUpdateBooksFormStage(b);
                System.out.println(SessionManager.getInstance().getCurrentUser().getId());
                bookFormController.getBookFormController().getBorrowerIDField().setText(Integer.toString(SessionManager.getInstance().getCurrentUser().getId()));
                LocalDate borrowDate = LocalDate.now();
                bookFormController.getBookFormController().getBorrowDateField().setText(borrowDate.toString());
                LocalDate returnDate = LocalDate.now().plusMonths(1);
                bookFormController.getBookFormController().getReturnDateField().setText(returnDate.toString());
                bookFormController.getBookFormController().Edit();
            }
        }
        if (user.getBorrowedBooks().indexOf(b) >= 0 && b.isBorrowed() == true) {
            System.out.println("Completed!");
        } 
        return String.format("Success! Please remember to return this book by %s", b.getReturnDate());
    }
}
