//package com.example.paint;
//
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.shape.StrokeLineCap;
//
//public class PaintDraw extends Canvas {
//    private GraphicsContext gc;
//
//
//    public DrawCanvas() {
//        super();
//        this.gc = this.getGraphicsContext2D();
//        this.gc.setLineCap(StrokeLineCap.ROUND);
//    }
//
//    public void drawLine(double x1, double y1, double x2, double y2)
//    {
//        gc.strokeLine(x1, y1, x2, y2);
//    }
//
//    public void setLineWidth(double width)
//    {
//        this.gc.setLineWidth(width);
//    }
//}