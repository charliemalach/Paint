package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Malachinski Pain(t) Application - ResizableCanvas.java
 * This class file is used to manage the canvas and its ability to be resized. Upon dragging the window, the canvas should resize with it and be editable.
 *
 **/

public class ResizableCanvas extends Canvas { //makes the canvas resizable when drawing

    public ResizableCanvas() {
        super();
        widthProperty().addListener(evt -> draw()); //adds listener to width
        heightProperty().addListener(evt -> draw()); //adds listener to height
    }

    private void draw() { //gets the new height and width of the canvas, draws a rect object that is now drawable
        double width = getWidth();
        double height = getHeight();
        GraphicsContext gc = getGraphicsContext2D();
        gc.rect(0, 0, width, height); //draws a new 'rect' that acts as the canvas with the new width and height.
    }

    @Override
    public boolean isResizable() { //overrides the resizable boolean
        return true;
    }

    @Override
    public double prefWidth(double height) { //overrides the preferred width
        return getWidth();
    }

    @Override
    public double prefHeight(double width) { //overrides the preferred height
        return getHeight();
    }
}
