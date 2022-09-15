package com.example.paint;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class PaintToolBar extends ToolBar {

    public final static String[] TOOLS = {"None", "Line"};
    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100};
    private static ComboBox<String> toolBox;
    private static ComboBox<Integer> widthBox;
    private static ColorPicker lineColorPicker;
    private static int usingTool;
    private static TextField numSides;
    private static int usingWidth;
    private static int usingNumSides;
    private static CheckBox setFill;

    public PaintToolBar() {
        super();
        usingWidth = 1;
        usingTool = 1;
        usingNumSides = 3;

        numSides = new TextField("3");
        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH));
        setFill = new CheckBox();

        lineColorPicker = new ColorPicker();

        getItems().addAll(new Label("Tools: "), toolBox, new Separator(),
                        new Label("Line Width: "), widthBox, new Label("Fill "), setFill, new Separator(),
                        new Label("Color: "), lineColorPicker
        );

        lineColorPicker.setValue(Color.BLACK);
        toolBox.setValue("None");
        widthBox.setPrefWidth(90);
        widthBox.setValue(1);

        numSides.setVisible(false);
        numSides.setPrefWidth(55);


        //listeners
        toolBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> {
            usingTool = newValue.intValue();
            System.out.println("Tool Selected: " + TOOLS[usingTool]);
        });

        widthBox.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if(!isNowFocused){
                if(Integer.parseInt(widthBox.getEditor().getText()) >= 1){
                    widthBox.setValue(Integer.parseInt(widthBox.getEditor().getText()));
                }
                else{
                    widthBox.setValue(1);
                }
            }
        });

        numSides.textProperty().addListener((observable, value, newValue) -> {
            if(Integer.parseInt(newValue) >= 3)
                usingNumSides = Integer.parseInt(newValue);
            else{
                numSides.setText("3");
            }
        });

        widthBox.setOnAction((ActionEvent e) -> {   //changes the value of usingWidth when the ComboBox is used/value changes
            usingWidth = widthBox.getValue();
            System.out.println("Width Selected: " + usingWidth);
        });
    }

    public static String getTool()
    {
        return TOOLS[usingTool];
    }

    public static int getLineWidth()
    {
        return usingWidth;
    }

}
