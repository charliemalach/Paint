package com.example.paint;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import static com.example.paint.Paint.root;
import static com.example.paint.Paint.toolbar;

public class PaintToolBar extends ToolBar {

    private final static String[] TOOLS = {"None", "Line"};

    private static final Integer[] LINE_WIDTH = {1, 2, 3, 5, 10, 15, 20, 25, 50, 100};

    private static ComboBox<String> toolBox;
    private static ComboBox<Integer> widthBox;
    private static int usingTool;
    private static int usingWidth;

    public PaintToolBar() {
        super();
        usingWidth = 1;
        usingTool = 0;

        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH));

        getItems().addAll(new Label("Tools: "), toolBox, new Separator(),
                        new Label("Line Width: "), widthBox, new Separator()
        );

        widthBox.setEditable(true);
        widthBox.setPrefWidth(90);
        widthBox.setValue(1);
    }

    public static int getLineWidth()
    {
        return usingWidth;
    }

    public static String getTool()
    {
        return TOOLS[usingTool];
    }
}
