package com.example.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import java.io.File;

public class PaintDraw extends ResizableCanvas { //extends the resizable canvas, allowing users to draw on extended part of canvas

    private GraphicsContext gc;
    private boolean shapeFill;

    public PaintDraw() { //default draw method
        super();
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void rectTool(double x1, double y1, double x2, double y2) { //draws a rectangle to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double width = Math.abs(x1 - x2);   //abs val of the two x's = length of x
        double height = Math.abs(y1 - y2);
        this.gc.strokeRect(x, y, width, height);
    }

    public void squareTool(double x1, double y1, double x2, double y2) { //draws a square to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double height;
        double width = height = Math.abs(x1 - x2);   //abs val of the two x's = length of x (will be the same because it's a square)
        this.gc.strokeRect(x, y, width, height);
    }

    public void polygonTool(double x1, double y1, double x2, double y2, int i) //stolen
    {
        double[] xPoints = new double[i];
        double[] yPoints = new double[i];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double startAngle = Math.atan2(y2 - y1, x2 - x1);

        for(int k = 0; k < i; k++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/i) + startAngle));
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/i) + startAngle));
        }
        if(this.getShapeFill())
            this.gc.fillPolygon(xPoints, yPoints, i);
        this.gc.strokePolygon(xPoints, yPoints, i);
    }

    public void triangleTool(double x1, double y1, double x2, double y2) {
        double x = x1; //set x to the smaller of the two values to map to bottom left
        double y = y1;
        double height;
        double width = height = (int) x^2 + (int) y^2 ;   //abs val of the two x's = length of x (will be the same because it's a square)
        this.gc.strokeRect(x, y, width, height);
    }

    public void ellipseTool(double x1, double y1, double x2, double y2) { //draws an ellipse to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //calculates the minimum of x1 and x2
        double y = (Math.min(y1, y2)); //calculates the minimum of y1 and y2
        double width = Math.abs(x1 - x2); //takes the absolute value of x1 - x2
        double height = Math.abs(y1 - y2); //takes the absolute value of y1 - y2
        this.gc.strokeOval(x, y, width, height);
    }

    public void circleTool(double x1, double y1, double x2, double y2) { //draws a circle to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //calculates the minimum of x1 and x2
        double y = (Math.min(y1, y2)); //calculates the minimum of y1 and y2
        double height;
        double width = height = Math.abs(x1 - x2); //takes the absolute value of x1 - x2 (these will be the same because it is a circle)

        this.gc.strokeOval(x, y, width, height);
    }

    public void lineTool(double x1, double y1, double x2, double y2) { //draws a line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        gc.strokeLine(x1, y1, x2, y2); //draws line from x1, y1 to x2, y2
    }

    public void lineDashes(double x)
    {
        gc.setLineDashes(x);

    }

    public void dashedLineTool(double x1, double y1, double x2, double y2) { //draws a dashed line to the canvas with the given parameters
        gc.setLineDashes(15); //sets dashes in the line
        gc.strokeLine(x1, y1, x2, y2); //draws dashed line from x1, y1 to x2, y2
    }

    public void drawPencilStart(double x1, double y1) { //draws the beginning of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public void drawPencilEnd(double x1, double y1) { //draws the end of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }


    public void drawEraserStart(double x1, double y1) { //draws the beginning of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public void drawEraserEnd(double x1, double y1) { //draws the end of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }


    public Color eyeDropper(double x, double y) { //selects the color at a specific pixel on the canvas
        return this.getRegion(x, y, x + 1, y + 1).getPixelReader().getColor(0, 0);
    }

    public Image getRegion(double x1, double y1, double x2, double y2) { //gets the 'region' parameters
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage wi = new WritableImage((int) Math.abs(x1 - x2), (int) Math.abs(y1 - y2));
        sp.setViewport(new Rectangle2D(
                (Math.min(x1, x2)),
                (Math.min(y1, y2)),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));
        this.snapshot(sp, wi);
        return wi;
    }

    public void drawImage(Image im) { //clears the canvas and draws an image to the canvas with the image height and width
        clearCanvas();
        this.setWidth(im.getWidth());
        this.setHeight(im.getHeight());
        this.gc.drawImage(im, 0, 0);
    }

    public void drawImage(File file) { //draws image to the screen
        if (file != null) {
            Image img = new Image(file.toURI().toString());
            this.drawImage(img);
        }
    }

    public void clearCanvas() { //clears the canvas of all content
        this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void setLineColor(Color color) { //sets the color of the line
        gc.setStroke(color);
    }

    public Color getLineColor() { //returns the current color of the line
        return (Color)gc.getStroke();
    }

    public void setFillColor(Color color) { //sets the fill color
        gc.setFill(color);
    }

    public boolean getShapeFill()
    {
        return this.shapeFill;
    }

    public void setShapeFill(boolean shapeFill)
    {
        this.shapeFill = shapeFill;
    }

    public void setLineWidth(double width) { //sets the width for the line
        this.gc.setLineWidth(width);
    }

    public void drawImageAt(Image im, double x, double y) { //draws image at specific parameters
        this.gc.drawImage(im, x, y);
    }

}
