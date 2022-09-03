package com.example.paint;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Malachinski - Pain(t)");

        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(saveAs);

        Menu editMenu = new Menu("Edit");
        MenuItem blank = new MenuItem("Blank");
        editMenu.getItems().add(blank);

        Menu toolMenu = new Menu("Tools");
        Menu helpMenu = new Menu("Help");

        MenuBar mb = new MenuBar();
        mb.getMenus().add(fileMenu);
        mb.getMenus().add(editMenu);
        mb.getMenus().add(toolMenu);
        mb.getMenus().add(helpMenu);

        VBox vb = new VBox(mb);




        Scene scene = new Scene(vb, 1280, 720);
        stage.setScene(scene);
        stage.show();


    }



    public static void main(String[] args) {
        launch();
    }
}