package com.example.paint;

import javafx.scene.canvas.Canvas;

import static com.example.paint.Paint.*;

public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for line coordinates


    public PaintCanvas()
    {
        super();
        //draws the beginning of line
        setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    System.out.println("onclick");
                    this.drawLine(x, y, x, y);

                    break;
                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });

        //follows the line being drawn
        setOnMouseDragged(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    this.drawLine(x, y, e.getX(), e.getY());

                    break;


                case("None"):
                    break;
            }
        });

        //draws complete line
        setOnMouseReleased(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    this.drawLine(x, y, e.getX(), e.getY());
                    break;


                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });
    }


}
