package com.example.paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Malachinski Pain(t) Application - PaintToolBar.java
 * This class file is used to manage the toolbar and its relevant methods. The color, line width, and number of sides (for specific shapes) are managed within this class file.
 *
 **/


public class PaintToolBar extends ToolBar {

    public final static String[] TOOLS = {"None", "Line", "Dashed Line", "Pencil", "Square", "Rectangle", "Polygon",
            "Triangle", "Ellipse", "Circle", "Color Dropper", "Eraser", "Copy", "Cut", "Paste", "Clear Canvas", "Rotate" , "Flip Horizontal",
            "Flip Vertical"};
    public final static String[] SAVES = {"Yes", "No"};
    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100}; //hard coded line widths for the user to use, might make this custom later
    public static ComboBox<String> toolBox; //creates a combo box to store all the available tools
    private static ComboBox<Integer> widthBox; //creates a combo box to store all the available widths
    private static ComboBox<String> saveBox;
    private static ColorPicker lineColorPicker; //creates the color picker for the line
    private static ColorPicker fillColorPicker; //creates the color picker for the fill of the object
    public static int usingTool; //creates the identifier for the array to observe which tool is being used
    private static int usingWidth; //creates a variable for the line width
    private static TextField sides; //creates an editable text field for the number of sides of the object
    private static int usingSides;
    private static int usingSave;
    private static TextField saveTime;
    private static int currentTime;



    public PaintToolBar() { //sets up the toolbar
        super();
        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS)); //adds all the defined tools to the toolbox
        saveBox = new ComboBox<>(FXCollections.observableArrayList(SAVES));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH)); //adds all the defined widths to the width selection box

        //setting all defaults:
        usingWidth = 1; //default line width
        usingTool = 0; //default tool = "none"
        usingSave = 0; //default auto save = "no"
        usingSides = 3; //default number of sides = 3
        currentTime = 45; //default save timer = 15 seconds

        sides = new TextField("3"); //sets the default sides in the editable text field

        lineColorPicker = new ColorPicker(); //creates color picker for the line
        fillColorPicker = new ColorPicker(); //creates color picker for the fill
        lineColorPicker.setValue(Color.BLACK); //default color = black
        toolBox.setValue("None"); //sets the default tool to "none"
        saveBox.setValue("Yes");
        widthBox.setValue(1); //sets the default width to 1
        saveTime = new TextField(Integer.toString(currentTime));

        Label seconds = new Label(" seconds");



        //adds items to toolbox
        getItems().addAll(new Label("Tools: "), toolBox, new Separator(), sides, new Separator(),
                new Label("Line Width: "), widthBox,
                new Label("Line Color: "), lineColorPicker, new Separator(),
                new Label("Auto-Save "), saveBox, saveTime, seconds
        );

        toolBox.setTooltip(new Tooltip("Select the desired tool."));
        widthBox.setTooltip(new Tooltip("Select the desired line width."));
        lineColorPicker.setTooltip(new Tooltip("Select the desired color."));
        saveBox.setTooltip(new Tooltip("Enable or disable auto-save."));

        seconds.setVisible(false); //makes the 'seconds' text box invisible
        saveTime.setVisible(true); //makes the text box visible
        sides.setVisible(false); //makes sides invisible until the proper tool is selected
        sides.setPrefWidth(50);
        saveTime.setPrefWidth(50);

        // Listeners for the tools

        toolBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> { //set new tool as selected tool
            usingTool = newValue.intValue();
            sides.setVisible(TOOLS[usingTool].equals("Polygon")); //enables the text input for the n-gon option and disables it otherwise
            System.out.println("Tool Selected: " + TOOLS[usingTool]);
        });

        saveBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> {
            usingSave = newValue.intValue();
            saveTime.setVisible(SAVES[usingSave].equals("Yes"));
            seconds.setVisible(SAVES[usingSave].equals("Yes"));
                });

            sides.textProperty().addListener((observable, value, newValue) -> { //parses the text input for sides so that it becomes a usable int value
            if(Integer.parseInt(newValue) >= 3)
                usingSides = Integer.parseInt(newValue);
            else{
                sides.setText("1");
            }
        });

        saveTime.textProperty().addListener((observable, value, newValue) -> { //sets the auto-save text box to the provided value
            if(Integer.parseInt(newValue) >=1)
                currentTime = Integer.parseInt(newValue);
            else{
                saveTime.setText("1");
            }
            Paint.getCurrentTab().updateSaveTimer();
        });

        widthBox.setOnAction((ActionEvent e) -> { //sets new line width as selected width
            usingWidth = widthBox.getValue();
            System.out.println("Width Selected: " + usingWidth); //print statement for debugging purposes
        });

    }

    public static String getTool() //returns current tool
    {
        return TOOLS[usingTool];
    }

    public static String getSave() //returns the save time
    {
        return SAVES[usingSave];
    }

    public static Color getLineColor() { //gets the color of the line
        return lineColorPicker.getValue();
    }
    public static Color getFillColor() { //gets the color of the fill object
      return fillColorPicker.getValue();
    }

    public static void setLineColor(Color color) { //sets the color of the line
        lineColorPicker.setValue(color);
    }

    public static void setFillColor(Color color){ //sets the fill color of the object
        fillColorPicker.setValue(color);
    }

    public static int getLineWidth() { //returns the width of the current object
        return usingWidth;
    }

    public static int getUsingSides() { //returns the current number of sides for the object
        return usingSides;
    }

    public static int getSaveTimer() //returns the current time for the auto-save timer
    {
        return currentTime;
    }
}
