package com.example.paint;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import static com.example.paint.Paint.*;
import static com.example.paint.Paint.line;


public class PaintToolBar extends ToolBar {

    public final static String[] TOOLS = {"None", "Line"};
    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100};
    private static ComboBox<String> toolBox;
    private static ComboBox<Integer> widthBox;
    private static ColorPicker lineColorPicker;
    public static int usingTool;
    private static TextField numSides;
    private static int usingWidth;
//    private static CheckBox setFill;
    private double x, y, x1, y1;

    public PaintToolBar() {
        super();
        usingWidth = 1;
        usingTool = 0;

        numSides = new TextField("3");
        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH));
        lineColorPicker = new ColorPicker();
//        setFill = new CheckBox();


        getItems().addAll(new Label("Tools: "), toolBox, new Separator(),
                        new Label("Line Width: "), widthBox,
                        new Label("Color: "), lineColorPicker
        );

        lineColorPicker.setValue(Color.BLACK);
        toolBox.setValue("None");
        widthBox.setPrefWidth(90);
        widthBox.setValue(1);

        numSides.setVisible(false);
        numSides.setPrefWidth(55);


        //listeners

        //set new tool
        toolBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> {
            usingTool = newValue.intValue();
            System.out.println("Tool Selected: " + TOOLS[usingTool]);
        });

//        //set new line width
//        widthBox.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
//            if(!isNowFocused){
//                if(Integer.parseInt(widthBox.getEditor().getText()) >= 1){
//                    widthBox.setValue(Integer.parseInt(widthBox.getEditor().getText()));
//                }
//                else{
//                    widthBox.setValue(1);
//                }
//            }
//        });

        //set new color
        lineColorPicker.setOnAction((ActionEvent e) -> {
            gc.setStroke(lineColorPicker.getValue());
        });

        //sets new line width
        widthBox.setOnAction((ActionEvent e) -> {
            usingWidth = widthBox.getValue();
            gc.setLineWidth(usingWidth);
            System.out.println("Width Selected: " + usingWidth);
        });


        //draws the beginning of line
        canvas.setOnMousePressed(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    System.out.println("onclick");
                    x = e.getX();
                    y = e.getY();
                    line.setStartX(x);
                    line.setStartY(y);
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getStartX(), line.getStartY());
                case("None"):
                    System.out.println("Nothing done");
                    break;
            }
        });

        //follows the line being drawn
        canvas.setOnMouseDragged(e -> {
            switch(PaintToolBar.getTool()){
                case("Line"):
                    System.out.println("ondrag");
                    x1 = e.getX();
                    y1 = e.getY();
                    line.setEndX(x1);
                    line.setEndY(y1);
//                    gc.strokeLine(line.getEndX(), line.getEndY(), line.getEndX(), line.getEndY());
                    break;
                case("None"):
                    break;
            }
        });

        //draws complete line
        canvas.setOnMouseReleased(e -> {
            switch(PaintToolBar.getTool())
            {
                case("Line"):
                    line.setEndX(e.getX());
                    System.out.println(e.getX());
                    line.setEndY(e.getY());
                    System.out.println(e.getY());
                    System.out.println("onrelease");
                    System.out.println();
                    gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                    break;
                case("None"):
                    break;
            }
        });
    }
    public static String getTool()
    {
        return TOOLS[usingTool];
    }
}
