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

public class Painters extends Application {

    ImageView picture = new ImageView();
    File file;
    File saved_file;
    Boolean Saving = true;
    public static File filepath;

    WritableImage wit = new WritableImage(200, 200);
    Canvas canvas = new Canvas();

    GraphicsContext gc = canvas.getGraphicsContext2D();
    public static WritableImage tmpSnap;
    public static WritableImage selImg;
    Canvas[] multiple = new Canvas[7];

    ScrollPane sp = new ScrollPane();

    @Override
    public void start(Stage stage) throws IOException {



        GridPane main = new GridPane();
        GridPane maincanvas = new GridPane();
        GridPane mainpicture = new GridPane();

        for (int p = 0; p < 7; p++) {
            multiple[p] = new Canvas();
            multiple[p].setHeight(canvas.getHeight());
            multiple[p].setWidth(canvas.getWidth());
        }

        canvas = multiple[0];

        gc = canvas.getGraphicsContext2D();

        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Image 1");

        tabpane.getTabs().add(tab1);

        tab1.setOnSelectionChanged(new EventHandler<Event>() {
                                       @Override
                                       public void handle(Event t) {
                                           if (tab1.isSelected()) {
                                               canvas = multiple[0];
                                               gc = canvas.getGraphicsContext2D();
                                               sp = new ScrollPane(canvas);
                                               sp.setPrefSize(650, 650);
                                               sp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                                               sp.setFitToWidth(true);
                                               sp.setFitToHeight(true);
                                               sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
                                               sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
                                               maincanvas.add(sp, 2, 2);
                                           }
                                       }
                                   }
        );



        Scene scene = new Scene(main, 1000, 900);
        stage.setTitle("Malachinski - Pain(t)");
        stage.setScene(scene);
        stage.show();

        MenuBar menuBar = new MenuBar();
        Menu File = new Menu("File");
        Menu Help = new Menu("Help");
        Menu Edit = new Menu("Edit");
        Menu Tool = new Menu("Tool");

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
                                     try { // resize image on canvas
                                         Image pic = new Image(file.toURI().toString());
                                         picture.setImage(pic);
                                         picture.setPreserveRatio(true);
                                         picture.setFitWidth(canvas.getWidth());
                                         picture.setSmooth(true);
                                         picture.setCache(true);

                                         BufferedImage img = ImageIO.read(file);
                                         WritableImage image = SwingFXUtils.toFXImage(img, null);
                                         GraphicsContext gc = canvas.getGraphicsContext2D();
                                         gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
                                     } catch (Exception ex) {
                                         System.out.println("Error");
                                         //System.Logger.getLogger(Painters.class.getName()).log(System.Logger.Level.SEVERE, null, ex);
                                     }
                                     Saving = false;
                                 }

                             }
                         }
        );

        MenuItem Save = new MenuItem("Save");

        Save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        Save.setMnemonicParsing(true);
        Save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (file != null) {
                    try {
                        WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(null, wi);
                        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
                        ImageIO.write(ri, "png", file);
                    } catch (IOException ex) {
                        //System.Logger.getLogger(Painters.class.getName()).log(System.Logger.Level.SEVERE, null, ex);
                    }
                    Saving = true;
                }
            }
        });


        MenuItem SaveAs = new MenuItem("Save As...");
        // File Chooser to Save as button to work
        SaveAs.setOnAction(new EventHandler<ActionEvent>() {
                               public void handle(ActionEvent event) {
                                   FileChooser fc = new FileChooser();
                                   fc.setTitle("Save As...");
                                   fc.getExtensionFilters().addAll(
                                           new FileChooser.ExtensionFilter("All Images", "*.*"),
                                           new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                                           new FileChooser.ExtensionFilter("ICON Files", "*.png"),
                                           new FileChooser.ExtensionFilter("JPG Files", ".jpg"),
                                           new FileChooser.ExtensionFilter("Documents", "*.*"),
                                           new FileChooser.ExtensionFilter("Desktop", "*.*"),
                                           new FileChooser.ExtensionFilter("Download", "*.*")
                                   );
                                   File save = fc.showSaveDialog(stage);
                                   if (save != null) {
                                       try {
                                           WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                                           canvas.snapshot(null, wi);
                                           RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
                                           ImageIO.write(ri, "png", save);
                                           file = save;
                                           saved_file = save;
                                       } catch (IOException ex) {
                                        //Logger.getLogger(Painters.class.getName()).log(Level.SEVERE, null, ex);
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
                String text = "Would you like to save?";
                exit.setContentText(text);

                Optional<ButtonType> show = exit.showAndWait();

                if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                    //for some reason file is null
                    try {
                        WritableImage wi = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(null, wi);
                        RenderedImage ri = SwingFXUtils.fromFXImage(wi, null);
                        ImageIO.write(ri, "png", saved_file);
                    } catch (IOException ex) {
                    //Logger.getLogger(Painters.class.getName()).log(Level.SEVERE, null, ex);
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

        menuBar.getMenus().add(File);
        menuBar.getMenus().add(Help);
        menuBar.getMenus().add(Edit);
        menuBar.getMenus().add(Tool);

        File.getItems().add(Open);
        File.getItems().add(Save);
        File.getItems().add(SaveAs);
        File.getItems().add(separator);
        File.getItems().add(Exit);

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