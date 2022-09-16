package com.example.paint;

import javafx.scene.canvas.Canvas;
import static com.example.paint.Paint.*;
import static com.example.paint.Paint.line;

public class PaintDraw extends Canvas {
    private double x, y, x1, y1; //variables used for line coordinates

    public PaintDraw()
    {
        super();
        //draws the beginning of line
        setOnMousePressed(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    System.out.println("onclick");
                    x = e.getX();
                    y = e.getY();
                    line.setStartX(x);
                    line.setStartY(y);
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getStartX(), line.getStartY());
                case("Pencil"):
                    gc.beginPath();
                    gc.moveTo(e.getX(), e.getY());
                    gc.stroke();

                    break;
                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });

        //follows the line being drawn
        setOnMouseDragged(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    System.out.println("ondrag");
                    x1 = e.getX();
                    y1 = e.getY();
                    line.setEndX(x1);
                    line.setEndY(y1);
                    break;
                case("Pencil"):
                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();

                case("None"):
                    break;
            }
        });

        //draws complete line
        setOnMouseReleased(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                    break;
                case("Pencil"):


                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });
    }
}
