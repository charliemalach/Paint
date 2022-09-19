package com.example.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.io.File;
import static com.example.paint.Paint.*;
import static com.example.paint.Paint.gc;

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

    public Image getRegion(double x1, double y1, double x2, double y2)
    {
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage wi = new WritableImage((int)Math.abs(x1 - x2), (int)Math.abs(y1-y2));

        sp.setViewport(new Rectangle2D(
                (x1 < x2 ? x1 : x2),
                (y1 < y2 ? y1 : y2),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));


        this.snapshot(sp, wi);
        return wi;
    }

    public void drawImage(Image im)
    {
        clearCanvas();
        this.setWidth(im.getWidth());
        this.setHeight(im.getHeight());
        this.gc.drawImage(im, 0, 0);
    }

    public void drawImage(File file)
    {
        if(file!=null){
            Image img = new Image(file.toURI().toString());
            this.drawImage(img);
        }
    }

    public void drawImageAt(Image im, double x, double y)
    {
        this.gc.drawImage(im, x, y);
    }

    public void clearCanvas()
    {
        this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
    }
}
