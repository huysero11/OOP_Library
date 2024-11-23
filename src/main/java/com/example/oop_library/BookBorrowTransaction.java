package com.example.oop_library;

import javax.swing.text.html.ImageView;
import java.time.LocalDate;

public class BookBorrowTransaction {

    private ImageView bookImage;

    public String borrowBooks(Books b, User user) {
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
                BookFormController bookFormController = new BookFormController();
                bookFormController.CreateUpdateBooksFormStage(books);
                System.out.println(SessionManager.getInstance().getCurrentUser().getId());
                bookFormController.getBookFormController().getBorrowerIDField().setText(Integer.toString(SessionManager.getInstance().getCurrentUser().getId()));
                LocalDate borrowDate = LocalDate.now();
                bookFormController.getBookFormController().getBorrowDateField().setText(borrowDate.toString());
                LocalDate returnDate = LocalDate.now().plusMonths(1);
                bookFormController.getBookFormController().getReturnDateField().setText(returnDate.toString());
                bookFormController.getBookFormController().Edit();
            }
        }
        if (user.getBorrowedBooks().indexOf(books) >= 0 && books.isBorrowed() == true) {
            System.out.println("Completed!");
        } 
        return String.format("Success! Please remember to return this book by %s", books.getReturnDate());
    }

    public ImageView getBookImage() {
        return bookImage;
    }

    public void setBookImage(ImageView bookImage) {
        this.bookImage = bookImage;
    }
}
