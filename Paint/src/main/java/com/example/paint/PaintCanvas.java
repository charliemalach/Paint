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

        mainPicture.setOnMouseClicked(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   this.x = e.getX();
                   this.y = e.getY();
                   System.out.println("onclick");
               case("None"):
                   break;
           }
       });

        mainPicture.setOnMouseDragged(e -> {
           switch(PaintToolBar.getTool()){
               case("Line"):
                   this.x1 = e.getX();
                   this.y1 = e.getY();
                   System.out.println("ondrag");
                    break;
               case("None"):
                   break;
           }
       });

        mainPicture.setOnMouseReleased(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   gc.strokeLine(x, y, x1, y1);
                   System.out.println("onrelease");
                   break;
               case("None"):
                   break;
           }
       });
    }

}
