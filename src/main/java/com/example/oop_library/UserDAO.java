package com.example.oop_library;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements DAOInterface<User> {

    public static int stt = 0;
    private static UserDAO instance;

    private UserDAO() {}

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public int add(User user) {
        int res = 0;
        try {
            Connection connection = MySQLConnection.getConnection();

            String addQuery = "INSERT INTO users (user_name, user_phone, user_password, user_admin) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, user.isAdmin());

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);

            // Get the generated key (user_id)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1)); // Set the generated ID in the user object
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }



    @Override
    public int update(User user) {
        int res = 0;

        try {
            Connection connection = MySQLConnection.getConnection();

            String updateQuery = "update users set user_name = ?, user_phone = ?, user_password = ?, user_admin = ? where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.setInt(5, user.getId());

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public int delete(User user) {
        int res = 0;
        try {
            Connection connection = MySQLConnection.getConnection();

            String deleteQuery = "delete from users where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, user.getId());

            res = preparedStatement.executeUpdate();
            System.out.println("Affected rows: " + res);
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> list = new ArrayList<>();

        try {
            Connection connection = MySQLConnection.getConnection();

            String getAllQuery = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String phoneNumber = resultSet.getString("user_phone");
                String password = resultSet.getString("user_password");
                boolean admin = resultSet.getBoolean("user_admin");

                User user = new User(id, name, phoneNumber, password, admin);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public User getById(int id) {
        User user = null;

        try {
            Connection connection = MySQLConnection.getConnection();
            String getByIdQuery = "select * from users where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String phoneNumber = resultSet.getString("user_phone");
                String password = resultSet.getString("user_password");
                boolean admin = resultSet.getBoolean("user_admin");

                user = new User(userId, userName, phoneNumber, password, admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getByPhoneNumber(String phoneNumber) {
        User user = null;

        try {
            Connection connection = MySQLConnection.getConnection();
            String getByPhoneNumberQuery = "select * from users where user_phone = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getByPhoneNumberQuery);
            preparedStatement.setString(1, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPhoneNumber = resultSet.getString("user_phone");
                String password = resultSet.getString("user_password");
                boolean admin = resultSet.getBoolean("user_admin");

                user = new User(userId, userName, phoneNumber, password, admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public ArrayList<User> getByCondition(String condition) {
        return null;
    }
}
