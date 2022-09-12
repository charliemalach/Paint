package com.example.paint;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import static com.example.paint.Paint.root;
import static com.example.paint.Paint.toolbar;

public class PaintToolBar extends ToolBar {



    public void CleanToolBar() {

        Button line = new Button("Line Tool");
        toolbar.getItems().addAll(line, new Separator());
        toolbar.setOrientation(Orientation.HORIZONTAL);
    }
}
