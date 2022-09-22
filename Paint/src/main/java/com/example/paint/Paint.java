package com.example.paint;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;


/**
 * Malachinski Pain(t) Application
 * The Pain(t) application is a program that currently allows users to upload, save and save as images. These images can be drawn upon and saved as new files. Users can use the following tools: Line, Dotted Line, Pencil, Square, Rectangle, and more. More features will come in the future.
 *
 * @author Charlie Malachinski
 * @version 1.0.2
 * @since 2022-09-19
 **/

public class Paint extends Application {
    private final static String TITLE = "Malachinski - Pain(t)"; //Name of the application
    private final static String VERSION = "v1.0.1";
    private final static int windowLength = 1280; //Dictates the initial length of the application window
    private final static int windowHeight = 720; //Dictates the initial width of the application window
    public static Stage mainStage; //Creates the main Stage
    public static BorderPane pane = new BorderPane();
    public static Image white = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\white.jpg");
    public static PaintToolBar toolbar = new PaintToolBar();
    public static PaintMenuBar menuBar = new PaintMenuBar(); //Creates a MenuBar
    public static TabPane tabpane;

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Paint.mainStage = stage;
        Image icon = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\icon.png"); //change to relative path instead of hard coded


        //layout setup
        tabpane = new TabPane(); //add new tabpane
        VBox topMenu = new VBox(menuBar, toolbar); //add menubar and toolbar to vbox object
        pane.setCenter(tabpane); //set tab to center of the main pane
        pane.setTop(topMenu); //set top menu to the top of the main pane
        tabpane.getTabs().add(new PaintTabs()); //adds new paint tab to the tabpane
//        tabpane.getSelectionModel().selectFirst();

        //starts scene
        scene = new Scene(pane, windowLength, windowHeight); //creates a new scene with the main Grid Pane and the desired application window size.
        stage.setTitle(TITLE + " - " + VERSION); //sets the title
        stage.getIcons().add(icon);
        stage.setScene(scene); //sets the scene
        stage.show(); //shows the scene on screen
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() { //smart save
            public void handle(WindowEvent we) {
                if (tabpane.getTabs() != null) //if the tab is active / has stuff in it, prompt user to save
                    Paint.getCurrentTab().quitProgram();
                else
                    stage.close();
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }

    public static PaintTabs getCurrentTab() {
        return (PaintTabs) tabpane.getSelectionModel().getSelectedItem();
    }

    public static void removeCurrentTab() {
        Paint.tabpane.getTabs().remove(Paint.getCurrentTab());
    }
}