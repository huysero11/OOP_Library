package com.example.oop_library;

public class SessionManager {
    
    private static SessionManager instance;

    private User currentUser;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void startSession(User currentUser) {
        if (currentUser != null) {
            this.currentUser = currentUser; // Start session and save user's info.
            try {
                // Get info about user's borrowed books list. 
                String condition = new String("user_id = " + currentUser.getId());
                currentUser.setBorrowedBooks(BooksDao.getInstance().getByCondition(condition));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } else {
            System.out.println("currentUser is null!");
        }
        
    }

    public void endSession() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
