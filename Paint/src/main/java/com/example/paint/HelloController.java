package com.example.paint;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class HelloController extends HelloApplication{
    @FXML

    ImageView myImageView;

    Image myImage = new Image(getClass().getResourceAsStream());

    public void displayImage(){
        myImageView.setImage(myImage);
    }



}