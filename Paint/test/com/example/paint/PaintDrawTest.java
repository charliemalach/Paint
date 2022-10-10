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

    public PaintDrawTest(){
    }

    /**
     * Tests the setLineWidth() method to determine if it works properly.
     */
    @Test
    public void testSetLineWidth() {
        System.out.println("setLineWidth test: ");
        double width = 1.0;
        PaintDraw test = new PaintDraw();
        test.setLineWidth(width);
        try{
            assertEquals(width, test.getLineWidth());
            System.out.println("setLineWidth was successful.\n");
        } catch (Exception ex) {
            System.out.println("setLineWidth was not successful." + ex);
        }
    }

    /**
     * Tests the setLineColor() method to determine if it works properly.
     */
    @Test
    public void testSetLineColor() {
        System.out.println("setLineColor test: ");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setLineColor(color);
        try {
            assertEquals(color, test.getLineColor());
            System.out.println("setLineColor was successful.\n");
        } catch (Exception ex){
            System.out.println("setLineColor was not successful." + ex);
        }
    }

    /**
     * Tests the setFillColor() method to determine if it works properly.
     */
    @Test
    public void testSetFillColor() {
        System.out.println("setFillColor test: ");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setFillColor(color);
        try {
            assertEquals(color, test.getFillColor());
            System.out.println("setFillColor was successful.\n");

        } catch (Exception ex){
            System.out.println("setFillColor was not successful." + ex);
        }
    }
}