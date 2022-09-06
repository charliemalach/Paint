package com.example.paint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;

/**
 *  Malachinski Pain(t) Application
 *  The Pain(t) application is a program that allows users to upload images.
 *
 * @author Charlie Malachinski
 * @version 1.0.0
 * @since 2022-09-03
 *
 **/

public class Painters extends Application {

    ImageView picture = new ImageView(); //Creates a new ImageView object
    File file; //Creates a local variable called 'file' for file management
    File saved_file; //Creates a local variable called 'saved_file' for edited files
    Boolean Saving = true; //Boolean to determine if file is saved
    Canvas canvas = new Canvas(); //Creates new Canvas object

    WritableImage wit = new WritableImage(200, 200); //Creates a new WritableImage object to construct images

    GraphicsContext gc = canvas.getGraphicsContext2D(); //Creates a GraphicsContext to draw calls to a Canvas using a buffer

    @Override
    public void start(Stage stage) throws IOException {
        GridPane main = new GridPane();
        GridPane maincanvas = new GridPane();
        GridPane mainpicture = new GridPane();

        //This section creates the Pain(t) scene
        Scene scene = new Scene(main, 1000, 900);
        stage.setTitle("Malachinski - Pain(t)");
//        Image icon = new Image("../src/icons/icon.png");          icon is not working
//        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        //This section creates the MenuBar that hosts File, Help, Edit, Tools
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty()); //extends width of entire program
        Menu File = new Menu("File");
        Menu Help = new Menu("Help");
        Menu Edit = new Menu("Edit");
        Menu Tool = new Menu("Tool");

        //Creates menu option to open an image on user project
        MenuItem Open = new MenuItem("Open");
        Open.setMnemonicParsing(
                true);
        Open.setAccelerator(
                new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        Open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) { //Open File
                FileChooser fc = new FileChooser();
                fc.setTitle("Open File");
                fc.getExtensionFilters().addAll(
                        new ExtensionFilter("All Images", "*.*"),
                        new ExtensionFilter("PNG Files", "*.png")
                );
                File file = fc.showOpenDialog(stage);
                if (file != null) {
                    try { // Presents and resizes the selected image on the canvas
                        Image pic = new Image(file.toURI().toString());
                        picture.setImage(pic);
                        picture.setPreserveRatio(true);
                        picture.setFitWidth(canvas.getWidth());
                        picture.setFitHeight(canvas.getHeight());
                        picture.setSmooth(true);
                        picture.setCache(true);
                        BufferedImage img = ImageIO.read(file);
                        WritableImage image = SwingFXUtils.toFXImage(img, null);
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
                        int imageHeight = (int) image.getHeight();
                        int imageWidth = (int) image.getWidth();
                        System.out.println(file.getAbsolutePath());
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                    Saving = false;
                }
            }
        }
        );

        MenuItem Save = new MenuItem("Save"); //Creates menu option to save current user project
        Save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)); //sets hotkey CTRL + S --> Save
        Save.setMnemonicParsing(true);

        Save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (file != null) { //this loop is broken, file is not currently set at the time of the loop running. it should be though.
                    try {
                        System.out.println("looking for path...");
                        System.out.println(file.getAbsolutePath());
                        WritableImage writableImage = new WritableImage((int) mainpicture.getWidth(), (int) mainpicture.getHeight()); //this code has errors
                        mainpicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        System.out.println(file.getAbsolutePath());
                        System.out.println("File successfully saved");
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }
                    Saving = true;
                }
            }
        });

        MenuItem SaveAs = new MenuItem("Save as..."); //Creates menu option to save current user project as different file
        SaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN)); //sets hotkey CTRL + Shift + S --> Save
        SaveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save As...");
                fc.getExtensionFilters().addAll( //allows user to save image as any of the following extensions
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                        new FileChooser.ExtensionFilter("ICON Files", "*.png"),
                        new FileChooser.ExtensionFilter("JPG Files", ".jpg")
                );
                File save = fc.showSaveDialog(stage);
                if (save != null) {
                    try {
                        System.out.println(mainpicture.getWidth() + mainpicture.getHeight());
                        WritableImage writableImage = new WritableImage((int) mainpicture.getWidth(), (int) mainpicture.getHeight()); //this code has errors
                        System.out.println(canvas.getWidth() + canvas.getHeight());
                        mainpicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", save);
                        file = save;
                        saved_file = save;
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }
                    Saving = true;
                }
            }
        }
        );

        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem Exit = new MenuItem("Exit", null);
        Exit.setMnemonicParsing(
                true);
        // Ctrl + X
        Exit.setAccelerator(
                new KeyCodeCombination(KeyCode.ESCAPE));
        Exit.setOnAction(e -> {
            if (Saving) {
                Platform.exit();
                System.exit(0);
            } else {
                Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
                exit.setTitle("File has not been Saved");
                String text = "Would you like to save? (Click Cancel to close project.)";
                exit.setContentText(text);
                Optional<ButtonType> show = exit.showAndWait();

                if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                    try {
                        WritableImage writableImage = new WritableImage((int) mainpicture.getWidth(), (int) mainpicture.getHeight()); //this code has errors
                        mainpicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", saved_file); //this sometimes breaks?
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }

                    //exits program
                    Platform.exit();
                    System.exit(0);
                } else {
                    //if cancel button is clicked
                    Platform.exit();
                    System.exit(0);
                }
            }
        });

        //This section adds all the File options to the menu bar
        menuBar.getMenus().add(File);
        File.getItems().add(Open);
        File.getItems().add(Save);
        File.getItems().add(SaveAs);
        File.getItems().add(separator);
        File.getItems().add(Exit);

        //This section adds the other main options to the menu bar
        menuBar.getMenus().add(Help);
        menuBar.getMenus().add(Edit);
        menuBar.getMenus().add(Tool);

        main.setHgap(0);
        main.setVgap(-5);
        main.addRow(1, menuBar);
        main.addRow(2, maincanvas);
        main.addRow(3, mainpicture);

        maincanvas.setHgap(100);
        maincanvas.setVgap(20);

        mainpicture.setHgap(100);
        mainpicture.setVgap(20);
        mainpicture.add(picture, 0, 1);
        mainpicture.minHeight(400);
        mainpicture.minWidth(400);
    }



    public static void main(String[] args) {
        launch();
    }
}