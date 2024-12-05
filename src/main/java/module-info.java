module com.example.oop_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;
    requires com.google.gson;
//    requires org.junit.jupiter.api;
//    requires org.mockito;


    opens com.example.oop_library to javafx.fxml,  com.google.gson;
    exports com.example.oop_library;
}