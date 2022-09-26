package com.example.paint;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.Stack;

import static com.example.paint.Paint.mainStage;


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

        setOnMouseMoved(e -> { //when mouse is moved at all on canvas
            switch (PaintToolBar.getTool()) {
                case ("Line"), ("Dashed Line"), ("Rectangle"), ("Pencil"), ("Ellipse"), ("Square"), ("Circle"), ("None"), ("Eraser"), ("Copy") ->
                        setCursor(Cursor.DEFAULT); //cursor is just default
                case ("Clear Canvas"), ("Paste") -> setCursor(Cursor.OPEN_HAND); //cursor is hand, couldn't think of better cursor

                case ("Color Dropper") -> setCursor(Cursor.CROSSHAIR); //cursor is crosshairs, thought it would be p cool
            }
        });

        setOnMousePressed(e -> { //when mouse is initially held down
            x = e.getX();
            y = e.getY();
            this.setLineColor(PaintToolBar.getLineColor()); //gets the desired line color from PaintToolBar class
            this.setLineWidth(PaintToolBar.getLineWidth()); //gets the desired line width from PaintToolBar class
            switch (PaintToolBar.getTool()) {
                case ("Line"):
                    this.lineDashes(0);
                    this.lineTool(x, y, x, y); //draws a line at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Dashed Line"):
                    this.dashedLineTool(x, y, x, y); //draws a dashed line at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Pencil"):
                    this.lineDashes(0);
                    this.drawPencilStart(x, y); //draws the beginning pencil mark at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Square"):
                    this.lineDashes(0);
                    this.squareTool(x, y, x, y); //draws a square at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Rectangle"):
                    this.lineDashes(0);
                    this.rectTool(x, y, x, y); //draws a rectangle at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Ellipse"):
                    this.lineDashes(0);
                    this.ellipseTool(x, y, x, y); //draws an ellipse at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Circle"):
                    this.lineDashes(0);
                    this.circleTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"):
                    this.lineDashes(0);
                    PaintToolBar.setLineColor(this.eyeDropper(x, y)); //changes cursor for color selector
                    break;

                case ("Eraser"):
                    this.lineDashes(0);
                    this.setLineColor(Color.WHITE);
                    this.drawEraserStart(x, y); //draws the beginning eraser mark at the beginning coordinates
                    this.updateCanvas();
                    break;

                case("Copy"):
//                    this.lineDashes(5);
                    this.setLineWidth(2);
                    this.setLineColor(Color.RED);
                    this.rectTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case("Paste"):
                    this.lineDashes(0);
                    try{
                        this.drawImageAt(image, e.getX(), e.getY());
                    }catch(Exception exception){
                        System.out.println(exception);
                    }
                    break;

                case ("Clear Canvas"):
                    //add onclick listener here
                        Alert clear = new Alert(Alert.AlertType.CONFIRMATION);
                        clear.setTitle("Clear Canvas?");
                        String text = "Would you like to clear the canvas? (Changes will not be saved.)";
                        clear.setContentText(text);
                        Optional<ButtonType> show = clear.showAndWait();
                        if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                            try {
                                this.clearCanvas();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            //After project is saved, program will exit.
                            this.updateCanvas();
                        } else {
                            this.updateCanvas();
                        }
                        break;

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
                    this.lineDashes(0);
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
                    this.lineDashes(0);
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();
                    break;

                case ("Eraser"):
                    this.lineDashes(0);
                    this.drawEraserEnd(x1, y1); //draws a pencil stroke to the end coordinates
                    this.updateCanvas();
                    break;

                case ("Copy"):
                    this.lineDashes(5);
                    this.undo();
                    this.rectTool(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Paste"):
                    this.lineDashes(0);
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
                    this.lineDashes(0);
                    this.lineTool(x, y, x1, y1); //draws a line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Dashed Line"):
                    this.lineDashes(0);
                    this.dashedLineTool(x, y, x1, y1); //draws a dashed line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    break;
                case ("Pencil"):
                    //does nothing, already drawn
                    break;

                case ("Square"):
                    this.lineDashes(0);
                    this.squareTool(x, y, x1, y1); //draws a square from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Rectangle"):
                    this.lineDashes(0);
                    this.rectTool(x, y, x1, y1); //draws a rectangle from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Ellipse"):
                    this.lineDashes(0);
                    this.ellipseTool(x, y, x1, y1); //draws an ellipse from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    break;

                case ("Circle"):
                    this.lineDashes(0);
                    this.circleTool(x, y, x1, y1);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"):
                    this.lineDashes(0);
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();
                    break;

                case ("Eraser"):
                    //does nothing
                    break;

                case ("Copy"):
                    this.lineDashes(0);
                    this.undo();
                    this.image = this.getRegion(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Paste"):
                    this.lineDashes(0);
                    this.undo();
                    if (this.image != null) {
                        this.drawImageAt(this.image, e.getX(), e.getY());
                    }
                    break;

                case ("None"):
                    System.out.println("Nothing done");
                    break;
            }
            this.updateCanvas();
        });
    }

    public void undo() //this is broken ?
    {
        PaintTabs.canvas.widthProperty().unbind(); //these break it
        PaintTabs.canvas.heightProperty().unbind(); //these break it
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

    public void redo() //this is broken too?
    {
        PaintTabs.canvas.widthProperty().unbind(); //these break it
        PaintTabs.canvas.heightProperty().unbind(); //these break it
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
