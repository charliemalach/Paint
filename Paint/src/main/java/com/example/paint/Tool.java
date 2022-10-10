package com.example.paint;

import javafx.scene.image.Image;

public class Tool {

    private String name;
    private Image image;


    public Image getImage(){
        return image;
    }

    private String getName(){
        return name;
    }

    private Tool(String name, Image image) {
        this.name = name;
        this.image = image;
    }


}
