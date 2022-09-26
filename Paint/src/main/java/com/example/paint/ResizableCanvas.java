package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas { //makes the canvas resizable when drawing

    public ResizableCanvas() {
        super();
        // listeners to redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() { //gets the new height and width of the canvas, draws a rect object that is now drawable
        double width = getWidth();
        double height = getHeight();
        GraphicsContext gc = getGraphicsContext2D();
        gc.rect(0, 0, width, height);
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
