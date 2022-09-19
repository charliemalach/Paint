package com.example.paint;

public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for line coordinates


    public PaintCanvas()
    {
        super();
        //draws the beginning of line
        setOnMousePressed(e -> {
            x = e.getX();
            y = e.getY();
            this.setLineColor(PaintToolBar.getLineColor());
            this.setLineWidth(PaintToolBar.getLineWidth());
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    System.out.println("onclick");
                    this.drawLine(x, y, x, y);
                    break;

                case("Dashed Line"):
                    this.drawDashedLine(x, y, x, y);
                    break;

                case("Pencil"):
                    this.drawPencilStart(x, y);
                    break;

                case("Square"):
                    this.drawSquare(x, y, x, y);
                    break;

                case("Rectangle"):
                    this.drawRect(x, y, x, y);
                    break;

                case("Ellipse"):
                    this.drawEllipse(x, y, x, y);
                    break;

                case("Circle"):

                    break;

                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });

        //follows the line being drawn
        setOnMouseDragged(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    break;

                case("Pencil"):
                    this.drawPencilEnd(x1, y1);
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

        //draws complete line
        setOnMouseReleased(e -> {
            x1 = e.getX();
            y1 = e.getY();
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    this.drawLine(x, y, x1, y1);
                    break;

                case("Dashed Line"):
                    this.drawDashedLine(x, y, x1, y1);
                case("Pencil"):
                    break;

                case("Square"):
                    this.drawSquare(x, y, x1, y1);
                    break;

                case("Rectangle"):
                    this.drawRect(x, y, x1, y1);
                    break;

                case("Ellipse"):
                    this.drawEllipse(x, y, x1, y1);
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
