package com.example.paint;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.paint.Paint.Logger;
import static com.example.paint.Paint.mainStage;

/**
 * Malachinski Pain(t) Application - PaintTabs.java
 * This class file is used to manage the changes made to individual tabs. This class also manages multiple canvases in the canvas stack, allowing for multiple canvases.
 *
 **/

public class PaintTabs extends Tab {

    public Pane CanvasPane; //creates a new pane to manage the canvas
    private static FileChooser chooseFile; //includes the file chooser
    private String title;
    private File path;
    public PaintCanvas canvas; //adds a new canvas object
    private ScrollPane sp; //creates the scroll pane
    public static StackPane canvasStack; //creates a canvas stack for multiple canvas objects
    private int autoSaveSec;
    private Timer autosaveTimer;
    private TimerTask autoSave;
    private final static int MILS_IN_SECS = 1000;
    private final boolean unsavedChanges;
    private final static String AUTOSAVE_DIR = "C:\\Users\\Charlie\\Documents\\GitHub\\Paint\\Paint\\src\\main\\resources\\images\\";

    public PaintTabs() { //sets the default tab 
        super();
        this.unsavedChanges = true;
        this.setText("New Tab");
        this.canvas = new PaintCanvas(); //modifies the current canvas
        tabStart(); //starts a new tab with the new canvas
    }

    public PaintTabs(File file) { //sets a new tab on image open 
        super();
        this.unsavedChanges = false;
        this.path = file;
        this.setText(path.getName());
        this.canvas = new PaintCanvas(); //modifies the current canvas
        tabStart();
    }

    private void tabStart() { //default constructor
        chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"), //handles .PNG files
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), //handles .JPG files
                new FileChooser.ExtensionFilter("Bitmap", "*.bmp"), //handles .BMP files
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"), //handles .JPEG files
                new FileChooser.ExtensionFilter("GIF", "*.gif") //handles .GIF files
        );

        //handles the canvas and the new stack
        this.CanvasPane = new Pane(canvas);
        this.canvasStack = new StackPane();
        this.canvasStack.getChildren().addAll(CanvasPane); //adds all canvas pane objects to the stack

        this.setOnCloseRequest(new EventHandler<Event>()
        {
            @Override
            public void handle(Event arg0)
            {
                quitTab(); //prompts the user to save before closing the current tab with the quitTab() method
            }
        });

        //handles the scroll pane on the canvas
        this.sp = new ScrollPane(this.canvasStack); //creates a scroll pane
        this.setContent(sp);
        this.sp.setPrefViewportWidth(this.canvas.getWidth() / 2);
        this.sp.setPrefViewportHeight(this.canvas.getHeight() / 2);

        this.autoSaveSec = PaintToolBar.getSaveTimer();
        this.autosaveTimer = new Timer();
        this.autoSave = new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        switch(PaintToolBar.getSave()) {
                            case ("No"):
                                System.out.println("Auto save is not enabled");
                                break;
                            case ("Yes"):
                                System.out.println("Auto save is enabled.");
                                autoSave();
                                break;
                        }
                    }
                });
            }
        };
        this.autosaveTimer.schedule(this.autoSave, 30000, (long) this.autoSaveSec *MILS_IN_SECS);
        Paint.getCurrentTab();
    }

    public void autoSave()
    { //WORKING FUNCTION - DO NOT FUCK WITH IT
        Paint.getCurrentTab();
        File backup = new File(AUTOSAVE_DIR + LocalDate.now() + Instant.now().toEpochMilli() + ".png");
        Image im = this.canvas.getRegion(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        try {
            if (this.path != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", backup);
//                new FileOutputStream(backup);
                this.setTitle(this.getFilePath().getName());
                System.out.println("Auto Save was Successful!");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void updateSaveTimer(){
        this.autoSaveSec = PaintToolBar.getSaveTimer();
        this.autoSave.cancel();
        this.autosaveTimer.purge();
        this.autoSave = new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        switch(PaintToolBar.getSave()) {
                            case "Yes": //if auto-save is selected
                                autoSave();
                                break;
                            case "No": //if auto-save is not selected
                                break;
                        }
                    }
                });
            }
        };
        this.autosaveTimer.schedule(this.autoSave, 0, (long) this.autoSaveSec *MILS_IN_SECS);
    }

    public void setFilePath(File path) { //sets the path for the current file
        this.path = path;
    }

    public File getFilePath() { //returns the path for the current file
        return this.path;
    }

    public static void openImage() { //uses file chooser to open an image stored in path and displays it on the canvas
        File path = chooseFile.showOpenDialog(mainStage);
        PaintTabs temp;
        if (path == null)
            temp = new PaintTabs();
        else
            temp = new PaintTabs(path);
        temp.canvas.drawImage(path);
        Paint.tabpane.getTabs().add(temp);
        Paint.tabpane.getSelectionModel().select(temp);
        try {
            FileWriter myWriter = new FileWriter(Logger);
            myWriter.write(LocalDate.now() + "" +  Instant.now().toString() + "- user opened image " + path);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void newTab() //opens a new tab on the canvas
    {
        PaintTabs newTab;
        newTab = new PaintTabs();
        Paint.tabpane.getTabs().add(newTab);
        Paint.tabpane.getSelectionModel().select(newTab);
    }

    public void saveImage() { //saves the original image
        Image im = this.canvas.getRegion(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        try {
            if (this.path != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", this.path);
                this.setTitle(this.getFilePath().getName());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void saveImageAs() { //saves image as a new image of desired file type
        Alert saveWarning = new Alert(Alert.AlertType.WARNING); //data loss warning
        saveWarning.setTitle("Data Loss Warning");
        saveWarning.setHeaderText("Potential Data Loss");
        String text = "Saving in a different file type may result in image features/data loss. Press 'OK' to continue save.";
        saveWarning.setContentText(text);
        saveWarning.showAndWait();

        File path = chooseFile.showSaveDialog(mainStage);
        this.setFilePath(path);
        this.saveImage();
    }

    public void saveImageAs(File path) { //saves existing image as a new image of desired file type

        Alert saveWarning = new Alert(Alert.AlertType.WARNING); //data loss warning
        saveWarning.setTitle("Data Loss Warning");
        saveWarning.setHeaderText("Potential Data Loss");
        String text = "Saving in a different file type may result in image features/data loss. Press 'OK' to continue save.";
        saveWarning.setContentText(text);
        saveWarning.showAndWait();

        this.setFilePath(path);
        this.saveImage();
    }

    public void quitProgram() //prompts the user to save before quitting program
    {
        if (this.path != null) {
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
            exit.setTitle("File has NOT been Saved");
            String text = "Would you like to save? (Click Cancel to close without saving.)";
            exit.setContentText(text);
            Optional<ButtonType> show = exit.showAndWait();
            if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                try {
                    Paint.getCurrentTab().saveImageAs();
                } catch (Exception ex) {
                    System.out.println(ex);
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
    }

    public void quitTab() //prompts the user to save before closing the current tab
    {
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
            exit.setTitle("File has NOT been Saved");
            String text = "Would you like to save? (Click Cancel to close without saving.)";
            exit.setContentText(text);
            Optional<ButtonType> show = exit.showAndWait();
            if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                try {
                    Paint.getCurrentTab().saveImageAs();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                //After project is saved, program will exit.
                Paint.removeCurrentTab();
            } else {
                //If Cancel button is clicked, program will just exit.
                return;
            }
    }

    public void setTitle(String title) { //sets the title of the current tab
        this.title = title;
        this.updateTitle(); //updates the title of tab
    }

    public File getPath() { //returns the path of the current file
        return this.path;
    }

    public void updateTitle() { //updates the title of the current tab to the name of the path
        if (this.path != null)
            this.title = this.path.getName();
        else
            this.setText(this.title);
    }

    public void undo() //UNDO CHANGES MADE TO CANVAS (IN THE STACK)
    {
        this.canvas.undo();
    }
    public void redo() //REDO CHANGES MADE TO CANVAS (IN THE STACK)
    {
        this.canvas.redo();
    }

    public void resizeCanvas(int x) //resizes the entire canvas proportional with the newly provided canvas width
    {
        this.canvas.setWidth(x); //sets the canvas width to the given int
        this.canvas.setHeight(x / 1.78); //sets the height. i use this to make it proportional. 1920 / 1080 = ~1.78, and 1280 / 720 =~1.78, so this made sense to me
    }
}
