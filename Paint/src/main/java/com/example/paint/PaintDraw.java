package com.example.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PaintDraw extends Canvas {
    private GraphicsContext gc;

    public PaintDraw(){
        super();
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void drawLine(double x1, double y1, double x2, double y2)
    {
        gc.strokeLine(x1, y1, x2, y2);
    }

    public void setLineWidth(double width)
    {
        this.gc.setLineWidth(width);
    }

    public double getLineWidth()
    {
        return this.gc.getLineWidth();
    }

    public Image getDrawArea(double x1, double y1, double x2, double y2)
    {
        SnapshotParameters snapP = new SnapshotParameters();
        WritableImage wi = new WritableImage((int)Math.abs(x1 - x2),(int)Math.abs(y1 - y2));

        snapP.setViewport(new Rectangle2D(
                (Math.min(x1, x2)),
                (Math.min(y1, y2)),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));

        this.snapshot(snapP, wi);
        return wi;
    }

    public void drawImage(Image imDraw)
    {
        this.setWidth(imDraw.getWidth());
        this.setHeight(imDraw.getHeight());
        this.gc.drawImage(imDraw, 0, 0);
    }

    public void setLineColor(Color color)
    {
        gc.setStroke(color);
    }


}