package com.example.oop_library;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NotificationController {

    @FXML
    private ImageView imageNotif;

    @FXML
    private Label textNotif;

    public NotificationController() {

    }

    public ImageView getImageNotif() {
        return imageNotif;
    }

    public void setImageNotif(String url) {
        this.imageNotif.setImage(new Image(getClass().getResourceAsStream(url)));
    }

    public Label getTextNotif() {
        return textNotif;
    }

    public void setTextNotif(String content) {
        textNotif.setText(content);
    }

}
