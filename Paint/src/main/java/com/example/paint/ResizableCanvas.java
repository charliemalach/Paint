package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {


    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
    }

}
