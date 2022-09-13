package com.example.paint;

public class PaintCanvas extends PaintDraw {

    private double x, y;

    public PaintCanvas()
    {
        super();
        x = 0;
        y = 0;

       this.setOnMousePressed(e -> {
           x = e.getX();
           y = e.getY();
           this.setLineWidth(PaintToolBar.getLineWidth());
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   this.drawLine(x, y, x, y);
                   break;
           }
       });

       this.setOnMouseDragged(e -> {
           switch(PaintToolBar.getTool()){
               case("Line"):
                this.drawLine(x, y, e.getX(), e.getY());
                break;
           }
       });

       this.setOnMouseReleased(e -> {
           switch(PaintToolBar.getTool())
           {
               case("Line"):
                   this.drawLine(x, y, e.getX(), e.getY());
                   break;
           }
       });
    }

}
