package com.example.paint;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Malachinski Pain(t) Application - PaintDraw.java
 * This test file is used to manage the unit tests for the application. This file runs unit tests on the line width function, line color function, and color fill functions.
 *
 **/

class PaintDrawTest {

    @Test
    public void testSetLineWidth() {
        System.out.println("setLineWidth");
        double width = 1.0;
        PaintDraw test = new PaintDraw();
        test.setLineWidth(width);
        assertEquals(1.0, test.getLineWidth(), 0.1);
    }

    @Test
    public void testSetLineColor() {
        System.out.println("setLineColor");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setLineColor(color);
        assertEquals(color, test.getLineColor());

    }

    @Test
    public void testSetFillColor() {
        System.out.println("setFillColor");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setFillColor(color);
        assertEquals(color, test.getFillColor());
    }
}