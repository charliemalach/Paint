package com.example.paint;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Malachinski Pain(t) Application - PaintDraw.java
 * This test file is used to manage the unit tests for the application. This file runs unit tests on the line width function, line color function, and color fill functions.
 *
 **/

class PaintDrawTest {

    public PaintDrawTest(){

    }
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Tests the setLineWidth() method to determine if it works properly. 
     */
    @Test
    public void testSetLineWidth() {
        System.out.println("setLineWidth test");
        double width = 1.0;
        PaintDraw test = new PaintDraw();
        test.setLineWidth(width);
        assertEquals(1.0, test.getLineWidth(), 0.1);
        System.out.println("setLineWidth was successful.");
    }

    /**
     * Tests the setLineColor() method to determine if it works properly.
     */
    @Test
    public void testSetLineColor() {
        System.out.println("setLineColor test");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setLineColor(color);
        assertEquals(color, test.getLineColor());
        System.out.println("setLineColor was successful.");
    }

    /**
     * Tests the setFillColor() method to determine if it works properly.
     */
    @Test
    public void testSetFillColor() {
        System.out.println("setFillColor");
        Color color = Color.BLACK;
        PaintDraw test = new PaintDraw();
        test.setFillColor(color);
        assertEquals(color, test.getFillColor());
        System.out.println("setFillColor was successful.");
    }
}