package com.example.paint;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class PaintToolBar extends ToolBar {
    public final static String[] TOOLS = {"None", "Line", "Dashed Line", "Pencil", "Square", "Rectangle", "Triangle", "Ellipse", "Circle", "Color Dropper", "Eraser", "Copy", "Cut", "Paste", "Clear Canvas"};
    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100};
    private static ComboBox<String> toolBox;
    private static ComboBox<Integer> widthBox;
    private static ColorPicker lineColorPicker;
    private static ColorPicker fillColorPicker;
    public static int usingTool;
    private static int usingWidth;

    private static TextField sides;

    private static int usingSides;


    public PaintToolBar() {
        super();

        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH));

        //setting all defaults:
        usingWidth = 1; //default line width
        usingTool = 0; //default tool = "none"
        usingSides = 3; //default number of sides = 3

        sides = new TextField("3");
        lineColorPicker = new ColorPicker();
        fillColorPicker = new ColorPicker();
        lineColorPicker.setValue(Color.BLACK); //default color = black
        toolBox.setValue("None");
        widthBox.setValue(1);


        //adds items to toolbox
        getItems().addAll(new Label("Tools: "), toolBox, sides, new Separator(),
                new Label("Line Width: "), widthBox,
                new Label("Line Color: "), lineColorPicker, new Separator()
        );

        sides.setVisible(false);
        sides.setPrefWidth(50);

        // Listeners!

        toolBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> { //set new tool as selected tool
            usingTool = newValue.intValue();
            System.out.println("Tool Selected: " + TOOLS[usingTool]);
        });

        sides.textProperty().addListener((observable, value, newValue) -> {
            if(Integer.parseInt(newValue) >= 3)
                usingSides = Integer.parseInt(newValue);
            else{
                sides.setText("3");
            }
        });

        widthBox.setOnAction((ActionEvent e) -> { //sets new line width as selected width
            usingWidth = widthBox.getValue();
            System.out.println("Width Selected: " + usingWidth);
        });
    }

    public static String getTool() //returns current tool
    {
        return TOOLS[usingTool];
    }

    public static Color getLineColor() {
        return lineColorPicker.getValue();
    }
    public static Color getFillColor() {
      return fillColorPicker.getValue();
    }

    public static void setLineColor(Color color) {
        lineColorPicker.setValue(color);
    }

    public static void setFillColor(Color color){
        fillColorPicker.setValue(color);
    }

    public static int getLineWidth() {
        return usingWidth;
    }

    public static int getUsingSides()
    {
        return usingSides;
    }

}
