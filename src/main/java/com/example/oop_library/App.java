package com.example.oop_library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {

    @Override
    public void start(@SuppressWarnings("exports") Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        Parent root = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
        arg0.setTitle("Dashboard");
        arg0.setScene(new Scene(root));
        arg0.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}