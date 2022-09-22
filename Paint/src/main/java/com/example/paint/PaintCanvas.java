package com.example.paint;

import javafx.scene.Cursor;

import static com.example.paint.Paint.mainStage;

public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for drawing

    public PaintCanvas() {
        super();
        

        setOnMouseMoved(e -> {
            switch (PaintToolBar.getTool()) {
                case ("Line"):
                case ("Dotted Line"):
                case ("Rectangle"):
                case ("Pencil"):
                case ("Ellipse"):
                case ("Square"):
                case ("Circle"):
                case ("None"):
                    setCursor(Cursor.DEFAULT);
                    break;
                case ("Color Dropper"):
                    setCursor(Cursor.CROSSHAIR);
                    break;
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
                    break;

                case ("Dashed Line"):
                    this.dashedLineTool(x, y, x, y); //draws a dashed line at the beginning coordinates
                    break;

                case ("Pencil"):
                    this.drawPencilStart(x, y); //draws the beginning pencil mark at the beginning coordinates
                    break;

                case ("Square"):
                    this.squareTool(x, y, x, y); //draws a square at the beginning coordinates
                    break;

                case ("Rectangle"):
                    this.rectTool(x, y, x, y); //draws a rectangle at the beginning coordinates
                    break;

                case ("Ellipse"):
                    this.ellipseTool(x, y, x, y); //draws an ellipse at the beginning coordinates
                    break;

                case ("Circle"):
                    this.circleTool(x, y, x, y);
                    break;

                case ("Color Dropper"):
                    PaintToolBar.setLineColor(this.eyeDropper(x, y)); //changes cursor for color selector
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
                    this.drawPencilEnd(x1, y1); //draws a pencil stroke to the end coordinates
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
                    break;

                case ("Dashed Line"):
                    this.dashedLineTool(x, y, x1, y1); //draws a dashed line from starting coordinates to new ending coordinates
                case ("Pencil"):
                    //does nothing, already drawn
                    break;

                case ("Square"):
                    this.squareTool(x, y, x1, y1); //draws a square from the starting coordinates to the new ending coordinates
                    break;

                case ("Rectangle"):
                    this.rectTool(x, y, x1, y1); //draws a rectangle from the starting coordinates to the new ending coordinates
                    break;

                case ("Ellipse"):
                    this.ellipseTool(x, y, x1, y1); //draws an ellipse from the starting coordinates to the new ending coordinates
                    break;

                case ("Circle"):
                    this.circleTool(x, y, x1, y1);
                    break;

                case ("Color Dropper"):
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    break;

                case ("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });
    }
}
