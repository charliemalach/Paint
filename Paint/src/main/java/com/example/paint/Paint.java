package com.example.paint;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *  Malachinski Pain(t) Application
 *  The Pain(t) application is a program that currently allows users to upload, save and save as images. These images can be drawn upon and saved as new files. More features will come in the future.
 *
 * @author Charlie Malachinski
 * @version 1.0.1
 * @since 2022-09-14
 *
 **/

public class Paint extends Application {
    private final static String TITLE = "Malachinski - Pain(t)"; //Name of the application
    private final static String VERSION = "v1.0.1";
    private final static int windowLength = 1280; //Dictates the initial length of the application window
    private final static int windowHeight = 720; //Dictates the initial width of the application window
    public static ImageView picture = new ImageView(); //Creates a new ImageView object
    public static File file; //Creates a local variable called 'file' for file management
    public static Group root = new Group();
    public static PaintToolBar toolbar = new PaintToolBar();
    public static File saved_file; //Creates a variable called 'saved_file' for edited files
    public static Boolean Saving = true; //Boolean to determine if file is saved
    public static GridPane main = new GridPane(); //Creates a new Grid Pane for the main application
    public static GridPane mainCanvas = new GridPane(); //Creates Grid Pane for the Canvas
    public static GridPane mainPicture = new GridPane(); //Creates Grid Pane for the Picture
    public static PaintMenuBar menuBar; //Creates a MenuBar
    public static Stage mainStage; //Creates the main Stage
    public static ScrollPane sp = new ScrollPane(mainPicture);
    public static PaintCanvas canvas = new PaintCanvas(); //Creates new Canvas object
    public static GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage stage) throws IOException {
        Paint.mainStage = stage; //creates the new stage for the application
        Scene scene = new Scene(main, windowLength, windowHeight); //creates a new scene with the main Grid Pane and the desired application window size.
        Image icon = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\icons\\icon.png");//relative path instead of hard coded
        stage.getIcons().add(icon);
        gc = canvas.getGraphicsContext2D();
        canvas = new PaintCanvas();
        toolbar = new PaintToolBar();
        menuBar = new PaintMenuBar(); //Creates a new menu bar
        menuBar.prefWidthProperty().bind(stage.widthProperty()); //extends width of entire program
        mainPicture.setAlignment(Pos.CENTER); //Aligns the picture to the center of the canvas

//      sp.setPannable(true);
        sp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        sp.setPrefSize(main.getWidth(), main.getHeight());
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setStyle("-fx-focus-color: transparent");
        root.getChildren().addAll(sp, toolbar, mainPicture, mainCanvas);

        //organizes canvas layout
        main.setHgap(0);
        main.setVgap(-5);
        main.add(menuBar, 0, 0);
        main.add(toolbar, 0, 1);
        main.add(sp, 0, 2);
        main.add(mainPicture, 0 , 2);

        //picture to canvas
        mainPicture.add(picture, 0, 2);
        mainCanvas.add(mainPicture, 0, 2);

        main.setVgap(1);
        mainCanvas.setHgap(10);
        mainCanvas.setVgap(1);  //not sure which part causes big pictures to open weird at the bottom, but whateva
        mainPicture.setVgap(1);

        //organizes stage
        stage.setTitle(TITLE + " - " + VERSION); //sets the title
        stage.setScene(scene); //sets the scene
        stage.show(); //shows the scene on screen
    }
    public static void main(String[] args) {
        launch();
    }
}