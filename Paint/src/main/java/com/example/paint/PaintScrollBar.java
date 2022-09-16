package com.example.paint;

import javafx.scene.control.ScrollPane;

import static com.example.paint.Paint.canvas;

public class PaintScrollBar extends ScrollPane{


    //scroll pane setup
    public PaintScrollBar()
    {
        super();
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setPrefSize(canvas.getWidth(), canvas.getHeight());
        setFitToWidth(true);
        setFitToHeight(true);
        hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        setStyle("-fx-focus-color: transparent");
        setContent(canvas);
        //setPannable(true);     buggy with drawing
    }

}
