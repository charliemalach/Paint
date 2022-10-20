package com.example.paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.File;

import static com.example.paint.Paint.pane;
import static com.example.paint.PaintTabs.logData;

/**
 * Malachinski Pain(t) Application - PaintDraw.java
 * This class file is used to manage the tools used to draw on the canvas. All the tool methods and their getters / setters are defined here. INCLUDES JAVADOC COMMENTING
 *
 **/
public class PaintDraw extends Canvas { //extends the resizable canvas, allowing users to draw on extended part of canvas

    private GraphicsContext gc;
    private boolean shapeFill;
    private Image image;

    public PaintDraw() { //default draw method
        super();
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }

    /**
     * Draws a rectangle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 - final y
     */
    public void rectTool(double x1, double y1, double x2, double y2) {
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double width = Math.abs(x1 - x2);   //abs val of the two x's = length of x
        double height = Math.abs(y1 - y2);
        if(this.getShapeFill())
            this.gc.fillRect(x, y, width, height);
        this.gc.strokeRect(x, y, width, height);
    }

    /**
     * draws a circle to the canvas from the coordinates x1,y1 to x2,y2
     * @param x1 - initial x
     * @param y1 - initial y
     * @param x2 - final x
     * @param y2 = final y
     */
    public void squareTool(double x1, double y1, double x2, double y2) { //draws a square to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //set x to the smaller of the two values to map to bottom left
        double y = (Math.min(y1, y2));
        double height;
        double width = height = Math.abs(x1 - x2);   //abs val of the two x's = length of x (will be the same because it's a square)
        this.gc.strokeRect(x, y, width, height);
    }

    /**
     * draws a polygon of s sides to the canvas with a center at x1, y1 and calculates radius with x2, y2.
     * @param x1 - x coordinate for center of polygon
     * @param y1 - y coordinate for center of polygon
     * @param x2 - x2 coordinate for
     * @param y2 - y2 coordinate for
     * @param s - s for desired sides on the polygon
     */
    public void polygonTool(double x1, double y1, double x2, double y2, int s){ //draws a polygon using the user's parameter (sides)
        double[] xPoints = new double[s];
        double[] yPoints = new double[s];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)); //big math calculation
        double startAngle = Math.atan2(y2 - y1, x2 - x1); //big math calculation
        for(int i = 0; i < s; i++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/s) + startAngle)); //big math calculation
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/s) + startAngle)); //big math calculation
        }
        this.gc.strokePolygon(xPoints, yPoints, s);
    }

    /**
     * draws a triangle to the canvas of s sides (hard coded to 3) to the canvas with a center at x1, y1 and calculates radius with x2, y2.
     * @param x1 - x coordinate for the center of polygon
     * @param y1 - y coordinate for the center of polygon
     * @param x2 - x2 coordinate for
     * @param y2 - y2 coordinate for
     * @param s - s for desired sides on the polygon
     */
    public void triangleTool(double x1, double y1, double x2, double y2, int s) {
        double[] xPoints = new double[s];
        double[] yPoints = new double[s];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)); //big math calculation
        double startAngle = Math.atan2(y2 - y1, x2 - x1); //big math calculation
        for(int i = 0; i < s; i++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/s) + startAngle)); //big math calculation
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/s) + startAngle)); //big math calculation
        }
        this.gc.strokePolygon(xPoints, yPoints, s);
    }

    /**
     * draws an ellipse to the canvas with the coordinates x1,y1 to x2, y2
     * @param x1 - x1 coordinate for initial x
     * @param y1 - y1 coordinate for initial y
     * @param x2 - x2 coordinate for final x
     * @param y2 - y2 coordinate for final y
     */
    public void ellipseTool(double x1, double y1, double x2, double y2) { //draws an ellipse to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //calculates the minimum of x1 and x2
        double y = (Math.min(y1, y2)); //calculates the minimum of y1 and y2
        double width = Math.abs(x1 - x2); //takes the absolute value of x1 - x2
        double height = Math.abs(y1 - y2); //takes the absolute value of y1 - y2
        this.gc.strokeOval(x, y, width, height);
    }

    /**
     * draws a circle to the canvas with the coordinates x1,x2 to y1,y2.
     * @param x1 - x1 coordinate for initial x
     * @param y1 - y1 coordinate for initial y
     * @param x2 - x2 coordinate for final x
     * @param y2 - y2 coordinate for final y
     */
    public void circleTool(double x1, double y1, double x2, double y2) { //draws a circle to the canvas with the given parameters
        double x = (Math.min(x1, x2)); //calculates the minimum of x1 and x2
        double y = (Math.min(y1, y2)); //calculates the minimum of y1 and y2
        double height;
        double width = height = Math.abs(x1 - x2); //takes the absolute value of x1 - x2 (these will be the same because it is a circle)

        this.gc.strokeOval(x, y, width, height);
    }

    /**
     * draws a line to the canvas from x1,y1 to x2,y2
     * @param x1 - x1 coordinate for initial x
     * @param y1 - y1 coordinate for initial y
     * @param x2 - x2 coordinate for final x
     * @param y2 - y2 coordinate for final y
     */
    public void lineTool(double x1, double y1, double x2, double y2) { //draws a line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        gc.strokeLine(x1, y1, x2, y2); //draws line from x1, y1 to x2, y2
    }

    /**
     * sets x dashes in a line for the given tool
     * @param x - x for number of dashes
     */
    public void lineDashes(double x) //sets the amount of dashes in the line
    {
        gc.setLineDashes(x);
    }

    /**
     * draws a dashed line to the canvas from x1,y1 to x2,y2
     * @param x1 - x1 coordinate for initial x
     * @param y1 - y1 coordinate for initial y
     * @param x2 - x2 coordinate for final x
     * @param y2 - y2 coordinate for final y
     */
    public void dashedLineTool(double x1, double y1, double x2, double y2) { //draws a dashed line to the canvas with the given parameters
        gc.setLineDashes(15); //sets dashes in the line
        gc.strokeLine(x1, y1, x2, y2); //draws dashed line from x1, y1 to x2, y2
    }

    /**
     * draws the start of a pencil mark to the canvas from x1 to y1
     * @param x1 - x1 for initial x
     * @param y1 - y1 for initial y
     */
    public void drawPencilStart(double x1, double y1) { //draws the beginning of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    /**
     * draws the end of a pencil mark to the canvas from x1 to y1
     * @param x1 - x1 for final x
     * @param y1 - y1 for final y
     */
    public void drawPencilEnd(double x1, double y1) { //draws the end of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0); //ensure there are no dashes in the pencil
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    /**
     * draws the start of an eraser mark to the canvas from x1 to y1
     * @param x1 - x1 for initial x
     * @param y1 - y1 for initial y
     */
    public void drawEraserStart(double x1, double y1) { //draws the beginning of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.beginPath();
        this.gc.moveTo(x1, y1);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    /**
     * draws the end of an eraser mark to the canvas from x1 to y1
     * @param x1 - x1 for final x
     * @param y1 - y1 for final y
     */
    public void drawEraserEnd(double x1, double y1) { //draws the end of a freehand line to the canvas with the given parameters
        gc.setLineDashes(0);
        this.gc.setLineCap(StrokeLineCap.ROUND);
        this.gc.lineTo(x1, y1);
        this.gc.stroke();
    }

    public void rotateImage()
    {
        this.setRotate(this.getRotate() + 90);
        Translate flipTranslate = new Translate(0, 0);
        this.getTransforms().add(flipTranslate);

        logData(" user rotated the image");
    }


    public void rotateTest()
    {
        Translate flipTranslate = new Translate(this.getHeight(), 0);
        Rotate flipRotation = new Rotate(90, Rotate.Y_AXIS);
        this.getTransforms().addAll(flipRotation, flipTranslate);
        logData(" user flipped the image vertically");
    }


    public void flipImageX()
    {
        Translate flipTranslate = new Translate(0, this.getHeight());
        Rotate flipRotation = new Rotate(180, Rotate.X_AXIS);
        this.getTransforms().addAll(flipTranslate, flipRotation);
        logData(" user flipped the image vertically");
    }

    public void flipImageY()
    {
        Translate flipTranslate = new Translate(this.getHeight(), 0);
        Rotate flipRotation = new Rotate(180, Rotate.Y_AXIS);
        this.getTransforms().addAll(flipTranslate, flipRotation);
        logData(" user flipped the image horizontally");
    }

    /**
     * gets the current color at coordinates x,y
     * @param x - x coordinate for the current color
     * @param y y coordinate for the current color
     * @return - the color at the given pixel
     */
    public Color eyeDropper(double x, double y) { //selects the color at a specific pixel on the canvas
        return this.getRegion(x, y, x + 1, y + 1).getPixelReader().getColor(0, 0);
    }

    /**
     * gets the current image on the canvas at coordinates x1,y1 and x2,y2
     * @param x1 - x1 is the coordinate starting at the top left of the canvas
     * @param y1 - y1 is the coordinate starting at the top left of the canvas
     * @param x2 - x2 is the coordinate ending at the bottom right of the canvas
     * @param y2 - y2 is the coordinate ending at the bottom right of the canvas
     * @return the image in the specified region
     */
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


    /**
     * draws an image to the canvas at given starting point
     * @param im - the image object being drawn to the canvas
     */
    public void drawImage(Image im) { //clears the canvas and draws an image to the canvas with the image height and width
        clearCanvas();
        this.setWidth(im.getWidth());
        this.setHeight(im.getHeight());
        this.gc.drawImage(im, 0, 0);
        this.setHeight(pane.getHeight());
        this.setWidth(pane.getWidth());
    }

    /**
     * draws a file to the canvas at given starting point
     * @param file - the file being drawn to the canvas
     */
    public void drawImage(File file) { //draws image to the screen
        if (file != null) {
            Image img = new Image(file.toURI().toString());
            this.drawImage(img);
        }
    }

    /**
     * clears the entire canvas
     */
    public void clearCanvas() {
        this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    /**
     * sets the color for the line being drawn to the canvas
     * @param color - the color to set the line
     */
    public void setLineColor(Color color) {
        gc.setStroke(color);
    }

    /**
     * gets the current color of the line
     * @return - returns the color of the current line
     */
    public Color getLineColor() {
        return (Color)gc.getStroke();
    }

    /**
     * sets the fill color of shapes and objects
     * @param color - the color to set the fill
     */
    public void setFillColor(Color color) {
        gc.setFill(color);
    }

    /**
     * gets the color of the current fill
     * @return - returns the color of the current fill
     */
    public Color getFillColor()
    {
        return (Color)gc.getFill();
    }

    /**
     * determines boolean value of color filled shape
     * @return - true/false: is shape filled?
     */
    public boolean getShapeFill()
    {
        return this.shapeFill;
    }

    /** sets the shapeFill variable to the boolean value
     * @param shapeFill - boolean that determines if shape is filled or not
     */
    public void setShapeFill(boolean shapeFill) //sets
    {
        this.shapeFill = shapeFill;
    }

    /**
     * sets the width of the current line
     * @param width - the width value of the current line
     */
    public void setLineWidth(double width) { //sets the width for the line
        this.gc.setLineWidth(width);
    }

    /**
     * gets the width of the current line
     * @return - returns the width value of the current line
     */
    public double getLineWidth()
    {
        return this.gc.getLineWidth();
    }

    /**
     * draws image to the canvas at the starting x,y coordinates
     * @param im - the image object being drawn to the canvas
     * @param x - x coordinate, top left of image
     * @param y - y coordinate, top left of image
     */
    public void drawImageAt(Image im, double x, double y) { //draws image at specific parameters
        this.gc.drawImage(im, x, y);
    }
}
