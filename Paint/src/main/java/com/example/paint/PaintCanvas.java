package com.example.paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static com.example.paint.Paint.*;

public class PaintCanvas extends PaintDraw { //TODO: make the fucking line draw

    private double x, y, x1, y1;

    public PaintCanvas()
    {
        super();
        setLineColor(Color.BLACK);
        setLineWidth(1);

        mainPicture.setOnMousePressed(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   System.out.println("onclick");
                   x = e.getX();
                   y = e.getY();
                   line.setStartX(x);
                   line.setStartY(y);
               case("None"):
                   break;
           }
       });

        mainPicture.setOnMouseDragged(e -> {
           switch(PaintToolBar.getTool()){
               case("Line"):
                   System.out.println("ondrag");
                   x1 = e.getX();
                   y1 = e.getY();
                   line.setEndX(x1);
                   line.setEndY(y1);
                   break;
               case("None"):
                   break;
           }
       });

        mainPicture.setOnMouseReleased(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   System.out.println("onrelease");
                   gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                   break;
               case("None"):
                   break;
           }
       });
    }

}
