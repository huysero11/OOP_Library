package com.example.oop_library;

import java.util.ArrayList;

public class DAOTest {
    public static void main(String[] args) {
//        User user = new User("huy", "092834", "huysiuu");
//        UserDAO.getInstance().add(user);
//
//        User user1 = new User("huyen", "029834", "huyenngu");
//        UserDAO.getInstance().add(user1);
//
//        System.out.println(user.getId() + " " + user1.getId());

        ArrayList<User>list = new ArrayList<>();
        list = UserDAO.getInstance().getAll();
        for (User user : list) {
            System.out.println(user.toString());
        }

        User user = UserDAO.getInstance().getById(3);
        System.out.println(user == null);

    }
}
