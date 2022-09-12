package com.example.paint;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import static com.example.paint.Paint.*;

public class PaintMenuBar extends MenuBar {

    public PaintMenuBar() {
        super();
        //This section creates the MenuBar that hosts File, Edit, Tools
        System.out.println("Menu successfully launched.");
        Menu File = new Menu("File");
        Menu Edit = new Menu("Edit");
        Menu Options = new Menu("Options");
        Menu Help = new Menu("Help");

        //This section adds the other main options to the menu bar
        getMenus().addAll(File, Edit, Options, Help);

        //'Open' menu item. Allows users to open a picture to the current project.
        MenuItem Open = new MenuItem("Open");
        Open.setMnemonicParsing(
                true);
        Open.setAccelerator(
                new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)); //sets hotkey CTRL + O --> Open Program
        Open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Open File");
                fc.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("PNG Files", "*.png")
                );
                File file = fc.showOpenDialog(mainStage);
                if (file != null) {
                    try { // Presents and resizes the selected image on the canvas
                        Image pic = new Image(file.toURI().toString());
                        picture.setImage(pic);
                        picture.setPreserveRatio(true);
                        picture.setSmooth(true);
                        picture.setCache(true);
                        mainPicture.setMaxWidth(picture.getFitWidth());
                        mainPicture.setMaxHeight(picture.getFitHeight());
                        BufferedImage img = ImageIO.read(file);
                        WritableImage image = SwingFXUtils.toFXImage(img, null);
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(image, picture.getFitWidth(), picture.getFitHeight());
                        //resizes canvas
                        mainStage.setWidth(pic.getWidth());
                        mainStage.setHeight(pic.getHeight());


                        System.out.println(file.getAbsolutePath() + " has been loaded.");
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                    Saving = false;
                }
            }
        }
        );

        //'Save' menu item. Allows users to save current project.
        MenuItem Save = new MenuItem("Save"); //Creates menu option to save current user project
        Save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)); //sets hotkey CTRL + S --> Save
        Save.setMnemonicParsing(true);
        Save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (file != null) {
                    try {
                        WritableImage writableImage = new WritableImage((int) mainPicture.getWidth(), (int) mainPicture.getHeight());
                        mainPicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        System.out.println("File successfully saved to " + file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }
                    Saving = true;
                }
            }
        });

        //'Save as' menu item. Allows users to save current project as a different file.
        MenuItem SaveAs = new MenuItem("Save as..."); //Creates menu option to save current user project as different file
        SaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)); //sets hotkey CTRL + Shift + S --> Save
        SaveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save As...");
                fc.getExtensionFilters().addAll( //allows user to save image as any of the following extensions
                        new FileChooser.ExtensionFilter("All Files", "*.*"),
                        new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                        new FileChooser.ExtensionFilter("ICON Files", "*.ico"),
                        new FileChooser.ExtensionFilter("JPG Files", ".jpg")
                );
                File save = fc.showSaveDialog(mainStage);
                if (save != null) {
                    try {
                        WritableImage writableImage = new WritableImage((int) mainPicture.getWidth(), (int) mainPicture.getHeight()); //this code has errors
                        mainPicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", save);
                        file = save;
                        saved_file = save;
                        System.out.println("File successfully saved to " + file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }
                    Saving = true;
                }
            }
        }
        );

        //'Exit' menu item. Allows users to exit current project after prompting them to save.
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem Exit = new MenuItem("Exit", null);
        Exit.setMnemonicParsing(
                true);
        Exit.setAccelerator(
                new KeyCodeCombination(KeyCode.ESCAPE)); //sets hotkey ESC --> Ends program
        Exit.setOnAction(e -> {
            if (Saving) {
                Platform.exit();
                System.exit(0);
            } else {
                Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
                exit.setTitle("File has NOT been Saved");
                String text = "Would you like to save? (Click Cancel to close without saving.)";
                exit.setContentText(text);
                Optional<ButtonType> show = exit.showAndWait();
                if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                    try {
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Save File");
                        fc.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("All Images", "*.*")
                        );
                        File file = fc.showOpenDialog(mainStage);
                        WritableImage writableImage = new WritableImage((int) mainPicture.getWidth(), (int) mainPicture.getHeight());
                        mainPicture.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                        System.out.println("File successfully saved to " + file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.out.println("Error has occurred.");
                    }
                    //After project is saved, program will exit.
                    Platform.exit();
                    System.exit(0);
                } else {
                    //If Cancel button is clicked, program will just exit.
                    Platform.exit();
                    System.exit(0);
                }
            }
        });

        //Creates the About section in the Help menu
        MenuItem About = new Menu("Help");
        About.setOnAction(e -> {
                    About.setMnemonicParsing(
                            true);
                    Alert aboutPaint = new Alert(Alert.AlertType.INFORMATION);
                    aboutPaint.setTitle("About Pain(t)");
                    String text = "Pain(t) is a JavaFX image handling project created for CS 250 by Charlie Malachinski.";
                    aboutPaint.setContentText(text);
                    Optional<ButtonType> show = aboutPaint.showAndWait();
        });

        //This section adds all the File options to the menu bar
        File.getItems().addAll(Open, Save, SaveAs, separator, Exit);
        //This section adds the About option under Help
        Help.getItems().add(About);
    }

}