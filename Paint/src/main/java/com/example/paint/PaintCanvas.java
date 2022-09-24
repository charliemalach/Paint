package com.example.paint;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Stack;


public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for drawing
    private Image image;
    private Stack<Image> undo;
    private Stack<Image> redo;

    public PaintCanvas() {
        super();
        //set defaults for undo/redo stack
        this.image = null;
        this.undo = new Stack<>();
        this.redo = new Stack<>();

        //set default background size & color
        this.setWidth(1280);
        this.setHeight(720);
        this.setFillColor(Color.WHITE);
        this.setLineColor(Color.WHITE);
        this.rectTool(0,0,this.getWidth(), this.getHeight());

        this.undo.push(this.getRegion(0, 0, this.getWidth(), this.getHeight()));

        setOnMouseMoved(e -> {
            switch (PaintToolBar.getTool()) {
                case ("Line"), ("Dotted Line"), ("Rectangle"), ("Pencil"), ("Ellipse"), ("Square"), ("Circle"), ("None"), ("Eraser"), ("Clear Canvas") ->
                        setCursor(Cursor.DEFAULT);
                case ("Color Dropper") -> setCursor(Cursor.CROSSHAIR);
            }
        });

        //when mouse is initially held down
        setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();
            this.setLineColor(PaintToolBar.getLineColor()); //gets the desired line color from PaintToolBar class
            this.setLineWidth(PaintToolBar.getLineWidth()); //gets the desired line width from PaintToolBar class
            switch (PaintToolBar.getTool()) {
                case ("Line"):
                    this.lineTool(x, y, x, y); //draws a line at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Dashed Line"):
                    this.dashedLineTool(x, y, x, y); //draws a dashed line at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Pencil"):
                    this.drawPencilStart(x, y); //draws the beginning pencil mark at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Square"):
                    this.squareTool(x, y, x, y); //draws a square at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Rectangle"):
                    this.rectTool(x, y, x, y); //draws a rectangle at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Ellipse"):
                    this.ellipseTool(x, y, x, y); //draws an ellipse at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Circle"):
                    this.circleTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"):
                    PaintToolBar.setLineColor(this.eyeDropper(x, y)); //changes cursor for color selector
                    break;

                case ("Eraser"):
                    this.setLineColor(Color.WHITE);
                    this.drawPencilStart(x, y); //draws the beginning eraser mark at the beginning coordinates
                    this.updateCanvas();
                    break;

                case("Copy"):
                    this.setLineWidth(2);
                    this.setLineColor(Color.BLACK);
                    this.rectTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case("Paste"):
                    try{
                        this.drawImageAt(image, e.getX(), e.getY());
                    }catch(Exception exception){
                        System.out.println(exception);
                    }
                    break;

                case ("Clear Canvas"):
                    //add onclick listener here
                    this.clearCanvas();
                    this.updateCanvas();

                case ("None"):
                    //does nothing
                    break;
            }
        });

        //when mouse is dragged on canvas
        setOnMouseDragged(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch (PaintToolBar.getTool()) {
                case ("Line"):
                    break;

                case ("Pencil"):
                    this.drawPencilEnd(x1, y1); //draws a pencil stroke to the end coordinates
                    this.updateCanvas();
                    break;

                case ("Square"):
                    //does nothing during drag event
                    break;

                case ("Rectangle"):
                    //does nothing during drag event
                    break;

                case ("Ellipse"):
                    //does nothing during drag event
                    break;

                case ("Circle"):
                    //does nothing during drag event
                    break;

                case ("Color Dropper"):
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();
                    break;

                case ("Eraser"):
                    this.drawPencilEnd(x1, y1); //draws a pencil stroke to the end coordinates
                    this.updateCanvas();
                    break;

                case ("Copy"):
                    this.undo();
                    this.rectTool(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Paste"):
                    this.undo();
                    try{
                        this.drawImageAt(image, e.getX(), e.getY());
                    }catch(Exception exception){
                        System.out.println(exception);
                    }
                    this.updateCanvas();
                    break;

                case ("None"):
                    //does nothing
                    break;
            }
        });

        //when mouse is released, the following graphics are drawn
        setOnMouseReleased(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch (PaintToolBar.getTool()) {
                case ("Line"):
                    this.lineTool(x, y, x1, y1); //draws a line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Dashed Line"):
                    this.dashedLineTool(x, y, x1, y1); //draws a dashed line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    break;
                case ("Pencil"):
                    //does nothing, already drawn
                    break;

                case ("Square"):
                    this.squareTool(x, y, x1, y1); //draws a square from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Rectangle"):
                    this.rectTool(x, y, x1, y1); //draws a rectangle from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Ellipse"):
                    this.ellipseTool(x, y, x1, y1); //draws an ellipse from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Circle"):
                    this.circleTool(x, y, x1, y1);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"):
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();
                    break;

                case ("Eraser"):
                    //does nothing
                    break;

                case ("Copy"):
                    this.undo();
                    this.image = this.getRegion(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Paste"):
                    this.undo();
                    if (this.image != null) {
                        this.drawImageAt(this.image, e.getX(), e.getY());
                    }
                    break;

                case ("None"):
                    System.out.println("Nothing done");
                    break;
            }
            Paint.getCurrentTab().updateTitle();
            this.updateCanvas();
        });
    }

    public void undo()
    {
        Image img = undo.pop();
        if(!undo.empty()){
            redo.push(img);
            this.drawImage(undo.peek());
        }
        else{
            this.drawImage(img);
            undo.push(img);
        }
    }

    public void redo()
    {
        if(!redo.empty()){
            Image im = redo.pop();
            undo.push(im);
            this.drawImage(im);
        }
    }

    public void updateCanvas()
    {
        undo.push(this.getRegion(0, 0, this.getWidth(), this.getHeight()));
        redo.clear();
    }

}
