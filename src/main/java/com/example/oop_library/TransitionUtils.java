package com.example.oop_library;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionUtils {
    public static void applyFadeTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
}
