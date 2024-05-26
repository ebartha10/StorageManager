package com.example.tp_tema3_fx.ui;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField passwordBox;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome");
    }
    @FXML
    protected void emailBoxOnMouseEntered(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), emailText);

        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void emailBoxOnMouseExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), emailText);

        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }
    @FXML
    protected void passwordBoxOnMouseEntered(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), passwordBox);

        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void passwordBoxOnMouseExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), passwordBox);

        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }
}