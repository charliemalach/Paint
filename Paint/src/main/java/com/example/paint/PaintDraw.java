package com.example.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.File;

import static com.example.paint.Paint.mainStage;

public class PaintDraw extends Canvas {

    private GraphicsContext gc;

    public PaintDraw() {
        super();
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void rectTool(double x1, double y1, double x2, double y2) {
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double w = Math.abs(x1 - x2);   //abs val of the two x's = length of x
        double h = Math.abs(y1 - y2);
        this.gc.strokeRect(x, y, w, h);
    }

    public void squareTool(double x1, double y1, double x2, double y2)
    {
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double w = Math.abs(x1 - x2);   //abs val of the two x's = length of x
        double h = Math.abs(x1 - x2);
        this.gc.strokeRect(x, y, w, h);
    }

    public void ellipseTool(double x1, double y1, double x2, double y2) {
        double x = (Math.min(x1, x2));
        double y = (Math.min(y1, y2));
        double w = Math.abs(x1 - x2);
        double h = Math.abs(y1 - y2);
        this.gc.strokeOval(x, y, w, h);
    }

    public void circleTool(double x1, double y1, double x2, double y2)
    {
        double x = (Math.min(x1, x2));
        double y = (Math.min(y1, y2));
        double w = Math.abs(x1 - x2);
        double h = Math.abs(x1 - x2);
        this.gc.strokeOval(x, y, w, h);
    }

    public void lineTool(double x1, double y1, double x2, double y2) {
        gc.setLineDashes(0);
        gc.strokeLine(x1, y1, x2, y2); //draws line from x1, y1 to x2, y2
    }

    public void dashedLineTool(double x1, double y1, double x2, double y2) {
        gc.setLineDashes(15); //sets dashes in the line
        gc.strokeLine(x1, y1, x2, y2); //draws dashed line from x1, y1 to x2, y2
    }

    public void drawPencilStart(double x1, double y1) {
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public void drawPencilEnd(double x1, double y1) {
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public Color eyeDropper(double x, double y) {
        return this.getRegion(x, y, x + 1, y + 1).getPixelReader().getColor(0, 0);
    }

    public Image getRegion(double x1, double y1, double x2, double y2) {
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage wi = new WritableImage((int) Math.abs(x1 - x2), (int) Math.abs(y1 - y2));

        sp.setViewport(new Rectangle2D(
                (x1 < x2 ? x1 : x2),
                (y1 < y2 ? y1 : y2),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));


        this.snapshot(sp, wi);
        return wi;
    }

    public void drawImage(Image im) {
        clearCanvas();
        this.setWidth(im.getWidth());
        this.setHeight(im.getHeight());
        this.gc.drawImage(im, 0, 0);
    }

    public void drawImage(File file) {
        if (file != null) {
            Image img = new Image(file.toURI().toString());
            this.drawImage(img);
        }
    }

    public void drawImageAt(Image im, double x, double y) {
        this.gc.drawImage(im, x, y);
    }

    public void clearCanvas() {
        this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void setLineColor(Color color) {
        gc.setStroke(color);
    }

    public void setLineWidth(double width) {
        this.gc.setLineWidth(width);
    }

    public double getLineWidth() {
        return this.gc.getLineWidth();
    }

}
