package com.example.oop_library;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.time.LocalDate;

public class BooksDao implements DAOInterface<Books> {

    private static BooksDao instance = null;

    private BooksDao() {

    }

    public static BooksDao getInstance() {
        if (instance == null) {
            instance = new BooksDao();
        }
        return instance;
    }

    @Override
    public int add(Books obj) {
        int res = 0;

        String addQuery = "INSERT INTO books (book_id, book_name, book_author, book_publication_year, "
                          + "borrowed_date, return_date, status, book_description, thumbnail, catagory, user_id) "
                          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
             
            preparedStatement.setString(1, obj.getBookID());
            preparedStatement.setString(2, obj.getBookName());
            preparedStatement.setString(3, obj.getBookAuthor());
            preparedStatement.setString(4, obj.getBookPublicationYear());
            preparedStatement.setDate(5, obj.getBorrowedDate() != null ? java.sql.Date.valueOf(obj.getBorrowedDate()) : null);
            preparedStatement.setDate(6, obj.getReturnDate() != null ? java.sql.Date.valueOf(obj.getReturnDate()) : null);
            preparedStatement.setBoolean(7, obj.isBorrowed());
            preparedStatement.setString(8, obj.getDescription());
            preparedStatement.setString(9, obj.getThumbNail());
            preparedStatement.setString(10, obj.getCatagory());
            if (obj.getBorrowerInfo() != null) {
                preparedStatement.setInt(11, obj.getBorrowerInfo().getId());
            } else {
                preparedStatement.setNull(11, java.sql.Types.INTEGER); // Set NULL for the foreign key
            }

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public int update(Books obj) {
        int res = 0;

        String updateQuery = "UPDATE books SET status = ?, user_id = ?, borrowed_date = ?, return_date = ? WHERE book_id = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setBoolean(1, obj.isBorrowed());
            if (obj.isBorrowed()) {
                if (obj.getBorrowerInfo() != null) {
                    preparedStatement.setInt(2, obj.getBorrowerInfo().getId());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.INTEGER);
                }
            } else {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            }
            preparedStatement.setDate(3, obj.getBorrowedDate() != null ? java.sql.Date.valueOf(obj.getBorrowedDate()) : null);
            preparedStatement.setDate(4, obj.getReturnDate() != null ? java.sql.Date.valueOf(obj.getReturnDate()) : null);
            preparedStatement.setString(5, obj.getBookID());

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public int delete(Books obj) {
        int res = 0;

        String deleteQuery = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, obj.getBookID());

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public ArrayList<Books> getAll() {
        ArrayList<Books> booksList = new ArrayList<>();

        String getAllQuery = "SELECT * FROM books";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Khởi tạo thông tin sách
                String bookID = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String bookAuthor = resultSet.getString("book_author");
                String bookPublicationYear = resultSet.getString("book_publication_year");
                LocalDate borrowedDate = resultSet.getDate("borrowed_date") != null ? resultSet.getDate("borrowed_date").toLocalDate() : null;
                LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;
                boolean borrowed = resultSet.getBoolean("status");
                String description = resultSet.getString("book_description");
                String thumbnail = resultSet.getString("thumbnail");
                String catagory = resultSet.getString("catagory");
                Integer id = resultSet.getObject("user_id", Integer.class);

                User borrowerInfo = (id != null) ? UserDAO.getInstance().getById(id) : null;

                // Tạo đối tượng sách và thêm vào danh sách
                Books books = new Books(bookID, bookName, bookAuthor, bookPublicationYear, thumbnail, catagory, description, borrowedDate, returnDate, borrowed, borrowerInfo);
                booksList.add(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }

    // Phương thức getById vẫn cần được triển khai
    @Override
    public Books getById(int id) {
        // TODO: implement method
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public ArrayList<Books> getByCondition(String condition) {
        ArrayList<Books> booksList = new ArrayList<>();

        String conditionQuery = "SELECT * FROM books WHERE " + condition; // Cảnh báo SQL injection ở đây

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(conditionQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Khởi tạo thông tin sách
                String bookID = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String bookAuthor = resultSet.getString("book_author");
                String bookPublicationYear = resultSet.getString("book_publication_year");
                LocalDate borrowedDate = resultSet.getDate("borrowed_date") != null ? resultSet.getDate("borrowed_date").toLocalDate() : null;
                LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;
                boolean borrowed = resultSet.getBoolean("status");
                String description = resultSet.getString("book_description");
                String thumbnail = resultSet.getString("thumbnail");
                String catagory = resultSet.getString("catagory");
                Integer id = resultSet.getObject("user_id", Integer.class);

                User borrowerInfo = (id != null) ? UserDAO.getInstance().getById(id) : null;

                // Tạo đối tượng sách và thêm vào danh sách
                Books books = new Books(bookID, bookName, bookAuthor, bookPublicationYear, thumbnail, catagory, description, borrowedDate, returnDate, borrowed, borrowerInfo);
                booksList.add(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }

    public Books getByID(String bookID) {
        Books books = null;

        String getByIdQuery = "SELECT * FROM books WHERE book_id = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {

            preparedStatement.setString(1, bookID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Khởi tạo thông tin sách
                    String bookName = resultSet.getString("book_name");
                    String bookAuthor = resultSet.getString("book_author");
                    String bookPublicationYear = resultSet.getString("book_publication_year");
                    LocalDate borrowedDate = resultSet.getDate("borrowed_date") != null ? resultSet.getDate("borrowed_date").toLocalDate() : null;
                    LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;
                    boolean borrowed = resultSet.getBoolean("status");
                    String description = resultSet.getString("book_description");
                    String thumbnail = resultSet.getString("thumbnail");
                    String catagory = resultSet.getString("catagory");
                    Integer id = resultSet.getObject("user_id", Integer.class);

                    User borrowerInfo = (id != null) ? UserDAO.getInstance().getById(id) : null;

                    // Tạo đối tượng sách
                    books = new Books(bookID, bookName, bookAuthor, bookPublicationYear, thumbnail, catagory, description, borrowedDate, returnDate, borrowed, borrowerInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
