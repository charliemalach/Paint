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
import java.io.File;
import java.io.IOException;

/**
 * Malachinski Pain(t) Application
 * The Pain(t) application is a program that currently allows users to upload, save and save as images. Auto-save has also been implemented. These images can be drawn upon and saved as new files. Users can use the following tools: Line, Dotted Line, Pencil, Square, Rectangle, and more. More features will come in the future.
 * The application also hosts a total of 3 unit tests to ensure that methods work properly.
 *
 * @author Charlie Malachinski
 * @version 1.0.5
 * @since 2022-10-14
 **/

public class Paint extends Application {
    private final static String TITLE = "Malachinski - Pain(t)"; //Name of the application
    private final static String VERSION = "v1.0.5"; //Version of the application
    public final static String IMAGES = "C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\"; //change these
    public final static String Logger = "C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\logger\\"; //changes these
    public static File test = new File(Logger + "log.txt"); //new logger file containing log data


    public final static int windowWidth = 1280; //Dictates the initial length of the application window
    public final static int windowHeight = 720; //Dictates the initial width of the application window
    public static Stage mainStage; //Creates the main Stage
    public static BorderPane pane = new BorderPane(); //Creates BorderPane for entire layout
    public static PaintToolBar toolbar = new PaintToolBar(); //Creates the Tool Bar
    public PaintMenuBar menuBar = new PaintMenuBar(); //Creates a MenuBar
    public static TabPane tabpane; //Creates a TabPane
    public static Scene scene; //Creates the Main Scene

    @Override
    public void start(Stage stage) throws IOException {

        //Initialize Layout Items
        Paint.mainStage = stage;
        Image icon = new Image("C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\icon.png"); //change to relative path instead of hard coded
        VBox topMenu = new VBox(menuBar, toolbar); //add MenuBar and ToolBar to vbox object

        //Layout setup
        tabpane = new TabPane(); //add new TabPane
        tabpane.getTabs().add(new PaintTabs()); //adds new paint tab to the TabPane
        pane.setCenter(tabpane); //set tab to center of the main pane
        pane.getChildren().add(PaintTabs.canvasStack);
        pane.setTop(topMenu); //set top menu to the top of the main pane


        //Starts scene
        scene = new Scene(pane, windowWidth, windowHeight); //creates a new scene with the main Grid Pane and the desired application window size.
        stage.setTitle(TITLE + " - " + VERSION); //sets the title
        stage.getIcons().add(icon);
        stage.setScene(scene); //sets the scene
        stage.show(); //shows the scene on screen

        //Create 'Smart Save' on application closing
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (tabpane.getTabs() != null) //if the tab is active / has stuff in it, prompt user to save
                    Paint.getCurrentTab().quitProgram();
                else
                    stage.close();
            }
        });
    }
    //Main launch function
    public static void main(String[] args){
        launch();
    }

    //returns the tab currently being used by the user
    public static PaintTabs getCurrentTab() {
        return (PaintTabs) tabpane.getSelectionModel().getSelectedItem();
    }
    //removes the tab currently being used by the user
    public static void removeCurrentTab() {
        Paint.tabpane.getTabs().remove(Paint.getCurrentTab());
    }
}