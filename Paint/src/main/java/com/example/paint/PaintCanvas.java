package com.example.paint;

import javafx.scene.paint.Color;

import static com.example.paint.Paint.*;

public class PaintCanvas extends PaintDraw {

    private double x, y;

    public PaintCanvas()
    {
        super();
        x = 0;
        y = 0;
//        this.setWidth(1280);
//        this.setHeight(720);
        this.setLineColor(Color.BLACK);
        this.setLineWidth(1);

        this.setOnMousePressed(e -> {
           x = e.getX();
           y = e.getY();
           this.setLineWidth(PaintToolBar.getLineWidth());
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   System.out.println("Line tool selected");
                   this.drawLine(x, y, x, y);
                   break;
               case("None"):
                   break;
           }
       });

        this.setOnMouseDragged(e -> {
           switch(PaintToolBar.getTool()){
               case("Line"):
                   System.out.println("Line tool selected");
                   this.drawLine(x, y, e.getX(), e.getY());
                   break;
               case("None"):
                   break;
           }
       });

        this.setOnMouseReleased(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   System.out.println("Line tool selected");
                   this.drawLine(x, y, e.getX(), e.getY());
                   break;
               case("None"):
                   break;
           }
       });
    }

}
