package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChartController {
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series<String, Integer> series = new XYChart.Series<>();

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;
    private CategoryAxis xBarAxis = new CategoryAxis();
    private NumberAxis yBarAxis = new NumberAxis();
    private XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
    public void initialize() {
        // Khởi tạo trục Y và X cho LineChart
        yAxis.setLabel("Number of books borrowed");
        lineChart.setTitle("Books Borrowed per Day");
        lineChart.getData().add(series);
        series.setName("Numbers");
        addDataToLineChart();

        pieChart.setTitle("Borrowed Books and Remaining Books");
        addDataToPieChart();

        xBarAxis.setLabel("Users");
        yBarAxis.setLabel("Number of books borrowed");
        barChart.setTitle("Number of books borrowed per User");
        barSeries.setName("Numbers");
        barChart.getData().add(barSeries);
        addDataToBarChart();
    }

    public void addDataToLineChart() {
        String query = """
                SELECT borrowed_date, COUNT(*) AS num_books_borrowed
                FROM books
                GROUP BY borrowed_date
                ORDER BY borrowed_date
                LIMIT 10;""";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            boolean dataFound = false;

            while (resultSet.next()) {
                String borrowedDateString = resultSet.getString("borrowed_date");
                if (borrowedDateString != null) {
                    LocalDate date = LocalDate.parse(borrowedDateString);
                    String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
                    int numBooksBorrowed = resultSet.getInt("num_books_borrowed");
                    series.getData().add(new XYChart.Data<>(formattedDate, numBooksBorrowed));
                    dataFound = true;
                }
            }

            if (!dataFound) {
                System.out.println("No data found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDataToPieChart() {
        String queryTotalBooks = "SELECT COUNT(*) AS total_books FROM books";
        String queryBorrowedBooks = "SELECT COUNT(*) AS borrowed_books FROM books WHERE borrowed_date IS NOT NULL";

        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement totalBooksStatement = connection.prepareStatement(queryTotalBooks);
            ResultSet totalBooksResult = totalBooksStatement.executeQuery();
            totalBooksResult.next();
            int totalBooks = totalBooksResult.getInt("total_books");

            PreparedStatement borrowedBooksStatement = connection.prepareStatement(queryBorrowedBooks);
            ResultSet borrowedBooksResult = borrowedBooksStatement.executeQuery();
            borrowedBooksResult.next();
            int borrowedBooks = borrowedBooksResult.getInt("borrowed_books");

            int remainingBooks = totalBooks - borrowedBooks;

            PieChart.Data borrowedData = new PieChart.Data("Borrowed", borrowedBooks);
            PieChart.Data remainingData = new PieChart.Data("Remaining", remainingBooks);

            pieChart.getData().clear();

            pieChart.getData().addAll(borrowedData, remainingData);

            Tooltip borrowedTooltip = new Tooltip("Books Borrowed: " + borrowedBooks);
            Tooltip remainingTooltip = new Tooltip("Books Remaining: " + remainingBooks);

            borrowedData.getNode().setOnMouseEntered(e -> {
                Tooltip.install(borrowedData.getNode(), borrowedTooltip);
            });

            remainingData.getNode().setOnMouseEntered(e -> {
                Tooltip.install(remainingData.getNode(), remainingTooltip);
            });

            borrowedData.getNode().setOnMouseExited(e -> Tooltip.uninstall(borrowedData.getNode(), borrowedTooltip));
            remainingData.getNode().setOnMouseExited(e -> Tooltip.uninstall(remainingData.getNode(), remainingTooltip));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDataToBarChart() {
        String query = """
                SELECT user_name, COUNT(*) AS num_books_borrowed
                FROM users u
                INNER JOIN books b ON u.user_id = b.user_id
                GROUP BY u.user_name
                LIMIT 8""";
        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("user_name");
                int count = resultSet.getInt("num_books_borrowed");
                System.out.println("User: " + userName + ", Books Borrowed: " + count);
                barSeries.getData().add(new XYChart.Data<>(userName, count));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
