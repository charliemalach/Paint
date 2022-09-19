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

public class PaintDraw extends Canvas {

    private GraphicsContext gc;
    private boolean fillShape;

    public PaintDraw()
    {
        super();
        this.fillShape = false;
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void drawRect(double x1, double y1, double x2, double y2){
        double x = (x1 < x2 ? x1 : x2); //set x to the smaller of the two values to map to bottom left
        double y = (y1 < y2 ? y1 : y2); //
        double w = Math.abs(x1 - x2);   //abs val of the two x's = length of x
        double h = Math.abs(y1 - y2);
        if(this.getFillShape())
            this.gc.fillRect(x,y,w,h);
        this.gc.strokeRect(x,y,w,h);
    }

    public void drawSquare(double x1, double y1, double x2, double y2){
        final double ANGLE_45 = Math.PI/4.0;    //pi/4 is a 45 degree angle, which makes it square instead of a diamond
        final int SIDES = 4;
        double[] xPoints = new double[SIDES];   //4 is the number of sides a square has
        double[] yPoints = new double[SIDES];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        //try and figure out how to fix later
        for(int i = 0; i < SIDES; i++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/4) + ANGLE_45));
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/4) + ANGLE_45));
        }
        if(this.getFillShape())
            this.gc.fillPolygon(xPoints, yPoints, SIDES);
        this.gc.strokePolygon(xPoints, yPoints, SIDES);
    }

    public void drawEllipse(double x1, double y1, double x2, double y2){
        double x = (x1 < x2 ? x1 : x2);
        double y = (y1 < y2 ? y1 : y2);
        double w = Math.abs(x1 - x2);
        double h = Math.abs(y1 - y2);
        if(this.getFillShape())
            this.gc.fillOval(x,y,w,h);
        this.gc.strokeOval(x,y,w,h);
    }

    public void drawLine(double x1, double y1, double x2, double y2)
    {
        gc.strokeLine(x1, y1, x2, y2);
    }

    public void drawDashedLine(double x1, double y1, double x2, double y2)
    {
       gc.setLineDashes(15);
       gc.strokeLine(x1, y1, x2, y2);
    }

    public void drawPencilStart(double x1, double y1)
    {
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public void drawPencilEnd(double x1, double y1)
    {
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
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

    public boolean getFillShape()
    {
        return this.fillShape;
    }

    public void drawImageAt(Image im, double x, double y)
    {
        this.gc.drawImage(im, x, y);
    }

    public void clearCanvas()
    {
        this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
    }

    public void setLineColor(Color color){gc.setStroke(color);}

    public void setLineWidth(double width){this.gc.setLineWidth(width);}

    public double getLineWidth(){return this.gc.getLineWidth();}

}
