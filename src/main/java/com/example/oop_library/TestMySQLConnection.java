package com.example.oop_library;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMySQLConnection {
    public static void main(String[] args) {
        try {
            Connection connection = MySQLConnection.getConnection();
//            System.out.println(connection);

            Statement statement = connection.createStatement();
            String query = "select * from users;";

            boolean check = statement.execute(query);
            System.out.println(check);


            MySQLConnection.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
