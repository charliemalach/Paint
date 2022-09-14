package com.example.paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static com.example.paint.Paint.*;

public class PaintCanvas extends PaintDraw { //TODO: Fix this entire class

    private double x, y, x1, y1;
    public Line line = new Line();

    public PaintCanvas()
    {
        super();
        setLineColor(Color.BLACK);
        setLineWidth(1);

        setOnMousePressed(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   this.x = e.getX();
                   this.y = e.getY();
               case("None"):
                   break;
           }
       });

        setOnMouseDragged(e -> {
           switch(PaintToolBar.getTool()){
               case("Line"):
                   this.x1 = e.getX();
                   this.y1 = e.getY();
                    break;
               case("None"):
                   break;
           }
       });

        setOnMouseReleased(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   this.drawLine(x, y, x1, y1);
                   break;
               case("None"):
                   break;
           }
       });
    }

}
