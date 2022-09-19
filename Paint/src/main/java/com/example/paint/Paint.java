package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


/**
 *  Malachinski Pain(t) Application
 *  The Pain(t) application is a program that currently allows users to upload, save and save as images. These images can be drawn upon and saved as new files. More features will come in the future.
 *
 * @author Charlie Malachinski
 * @version 1.0.1
 * @since 2022-09-16
 *
 **/

public class Paint extends Application {
    private final static String TITLE = "Malachinski - Pain(t)"; //Name of the application
    private final static String VERSION = "v1.0.1";
    private final static int windowLength = 1280; //Dictates the initial length of the application window
    private final static int windowHeight = 720; //Dictates the initial width of the application window
    public static Stage mainStage; //Creates the main Stage
    public static BorderPane pane = new BorderPane();
    public static File file; //Creates a local variable called 'file' for file management
    public static Image white = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\white.jpg");;
    public static File saved_file; //Creates a variable called 'saved_file' for edited files
    public static Boolean Saving = true; //Boolean to determine if file is saved
    public static PaintToolBar toolbar = new PaintToolBar();
    public static PaintMenuBar menuBar = new PaintMenuBar(); //Creates a MenuBar
    public static Line line = new Line();
    public static TabPane tabpane;

    @Override
    public void start(Stage stage) throws IOException {
        Paint.mainStage = stage;
        Image icon = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\icon.png"); //change to relative path instead of hard coded

        tabpane = new TabPane();
        //picture to canvas
        VBox topMenu = new VBox(menuBar, toolbar);

        //layout setup

        pane.setCenter(tabpane);
        pane.setTop(topMenu);

        tabpane.getTabs().add(new PaintTabs());
        tabpane.getSelectionModel().selectFirst();

        //starts scene
        Scene scene = new Scene(pane, windowLength, windowHeight); //creates a new scene with the main Grid Pane and the desired application window size.
        stage.setTitle(TITLE + " - " + VERSION); //sets the title
        stage.getIcons().add(icon);
        stage.setScene(scene); //sets the scene
        stage.show(); //shows the scene on screen
    }
    public static void main(String[] args) {
        launch();
    }

    public static PaintTabs getCurrentTab()
    {
        return (PaintTabs)tabpane.getSelectionModel().getSelectedItem();
    }

    public static void removeCurrentTab()
    {
        Paint.tabpane.getTabs().remove(Paint.getCurrentTab());
    }
}