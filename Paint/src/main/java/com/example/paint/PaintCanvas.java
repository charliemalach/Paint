package com.example.paint;

public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for line coordinates


    public PaintCanvas()
    {
        super();
        //when mouse is initially held down
        setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();
            this.setLineColor(PaintToolBar.getLineColor()); //gets the desired line color from PaintToolBar class
            this.setLineWidth(PaintToolBar.getLineWidth()); //gets the desired line width from PaintToolBar class
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    this.drawLine(x, y, x, y); //draws a line at the beginning coordinates
                    break;

                case("Dashed Line"):
                    this.drawDashedLine(x, y, x, y); //draws a dashed line at the beginning coordinates
                    break;

                case("Pencil"):
                    this.drawPencilStart(x, y); //draws the beginning pencil mark at the beginning coordinates
                    break;

                case("Square"):
                    this.drawSquare(x, y, x, y); //draws a square at the beginning coordinates
                    break;

                case("Rectangle"):
                    this.drawRect(x, y, x, y); //draws a rectangle at the beginning coordinates
                    break;

                case("Ellipse"):
                    this.drawEllipse(x, y, x, y); //draws an ellipse at the beginning coordinates
                    break;

                case("Circle"):

                    break;

                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });

        //when mouse is dragged on canvas
        setOnMouseDragged(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    break;

                case("Pencil"):
                    this.drawPencilEnd(x1, y1); //draws a pencil stroke to the end coordinates
                    break;

                case("Square"):

                    break;

                case("Rectangle"):

                    break;

                case("Ellipse"):

                    break;

                case("Circle"):

                    break;

                case("None"):
                    break;
            }
        });

        //when mouse is released, the following graphics are drawn
        setOnMouseReleased(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    this.drawLine(x, y, x1, y1); //draws a line from starting coordinates to new ending coordinates
                    break;

                case("Dashed Line"):
                    this.drawDashedLine(x, y, x1, y1); //draws a dashed line from starting coordinates to new ending coordinates
                case("Pencil"):
                    break;

                case("Square"):
                    this.drawSquare(x, y, x1, y1); //draws a square from the starting coordinates to the new ending coordinates
                    break;

                case("Rectangle"):
                    this.drawRect(x, y, x1, y1); //draws a rectangle from the starting coordinates to the new ending coordinates
                    break;

                case("Ellipse"):
                    this.drawEllipse(x, y, x1, y1); //draws an ellipse from the starting coordinates to the new ending coordinates
                    break;

                case("Circle"):

                    break;
                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });
    }


}