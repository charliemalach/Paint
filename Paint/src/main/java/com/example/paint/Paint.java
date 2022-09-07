package com.example.paint;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 *  Malachinski Pain(t) Application
 *  The Pain(t) application is a program that currently allows users to upload, save and save as images. More features will come with future updates.
 *
 * @author Charlie Malachinski
 * @version 1.0.0
 * @since 2022-09-03
 *
 **/

public class Paint extends Application {
    private final static String TITLE = "Malachinski - Pain(t)"; //Name of the application
    private final static String VERSION = "1.0.0";
    private final static int windowLength = 1280; //Dictates the initial length of the application window
    private final static int windowHeight = 720; //Dictates the initial width of the application window
    public static ImageView picture = new ImageView(); //Creates a new ImageView object
    public static File file; //Creates a local variable called 'file' for file management
    public static File saved_file; //Creates a variable called 'saved_file' for edited files
    public static  Boolean Saving = true; //Boolean to determine if file is saved
    public static  Canvas canvas = new Canvas(); //Creates new Canvas object
    public static GridPane main = new GridPane(); //Creates a new Grid Pane for the main application
    public static GridPane mainCanvas = new GridPane(); //Creates Grid Pane for the Canvas
    public static GridPane mainPicture = new GridPane(); //Creates Grid Pane for the Picture
    public static PaintMenuBar menuBar; //Creates a MenuBar
    public static Stage mainStage; //Creates the main Stage

    @Override
    public void start(Stage stage) throws IOException {
        Paint.mainStage = stage; //creates the new stage for the application
        Scene scene = new Scene(main, windowLength, windowHeight); //creates a new scene with the main Grid Pane and the desired application window size.
        menuBar = new PaintMenuBar(); //Creates a new menu bar
        menuBar.prefWidthProperty().bind(stage.widthProperty()); //extends width of entire program
        mainPicture.add(picture, 0, 0); //Adds the image to the canvas at the row 0, column 0.
        mainPicture.setAlignment(Pos.CENTER); //Aligns the picture to the center of the canvas


        //organizes canvas layout
        main.addRow(1, menuBar); //adds the menu bar to the scene
        main.addRow(2, mainCanvas); //adds the main canvas to the scene
        main.addRow(3, mainPicture); //adds the main picture to the scene

        //organizes stage
        stage.setTitle(TITLE + " - " + VERSION); //sets the title
        stage.setScene(scene); //sets the scene
        stage.show(); //shows the scene on screen
    }

    public static void main(String[] args) {
        launch();
    }
}