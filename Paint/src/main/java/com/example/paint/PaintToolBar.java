package com.example.paint;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class PaintToolBar extends ToolBar {

    private final static String[] TOOLS = {"None", "Line"};
    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100};

    private static ComboBox<String> toolBox;
    private static ComboBox<Integer> widthBox;

    private static ColorPicker lineColorPicker;
    private static int usingTool;
    private static int usingWidth;
    private static CheckBox setFill;

    public PaintToolBar() {
        super();
        usingWidth = 1;
        usingTool = 0;

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
        widthBox.setEditable(true);
        widthBox.setPrefWidth(90);
        widthBox.setValue(1);

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

//        widthBox.setOnAction((ActionEvent e) -> {   //changes the value of usingWidth when the ComboBox is used/value changes
//            usingWidth = widthBox.getValue();
//        });
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
