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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import static com.example.paint.Paint.*;

/**
 * Malachinski Pain(t) Application - PaintTabs.java
 * This class file is used to manage the changes made to individual tabs. This class also manages multiple canvases in the canvas stack, allowing for multiple canvases.
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

    /**
     * Creates a new tab with the opened image
     * @param file - image opened to the canvas
     */
    public PaintTabs(File file) { //sets a new tab on image open
        super();
        this.unsavedChanges = false;
        this.path = file;
        this.setText(path.getName());
        this.canvas = new PaintCanvas(); //modifies the current canvas
        tabStart();
    }

    /**
     * Starts a new blank tab and allows user to select image
     */
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

    /**
     * Auto saves tab to auto save path
     */
    public void autoSave()
    {
        Paint.getCurrentTab();
        File backup = new File(AUTOSAVE_DIR + LocalDate.now() + Instant.now().toEpochMilli() + ".png");
        Image im = this.canvas.getRegion(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        try {
            if (this.path != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", backup);
                this.setTitle(this.getFilePath().getName());
                System.out.println("Auto Save was Successful!");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Allows user to update timer for auto save
     */
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

    /**
     * Sets the path for the file
     * @param path - path set for file object
     */
    public void setFilePath(File path) { //sets the path for the current file
        this.path = path;
    }

    /**
     * Gets the path for the file
     * @return - returns the file object's current path
     */
    public File getFilePath() { //returns the path for the current file
        return this.path;
    }

    /**
     * Allows user to open an image on the current tab
     */
    public static void openImage(){ //uses file chooser to open an image stored in path and displays it on the canvas
        File path = chooseFile.showOpenDialog(mainStage);
        PaintTabs temp;
        if (path == null)
            temp = new PaintTabs();
        else
            temp = new PaintTabs(path);
        temp.canvas.drawImage(path);
        Paint.tabpane.getTabs().add(temp);
        Paint.tabpane.getSelectionModel().select(temp);
        logData(" user opened a new image");
    }

    /**
     * Allows user to create a new blank tab on the stack
     */
    public static void newTab() //opens a new tab on the canvas
    {
        PaintTabs newTab;
        newTab = new PaintTabs();
        Paint.tabpane.getTabs().add(newTab);
        Paint.tabpane.getSelectionModel().select(newTab);
        logData(" user opened a new tab");
    }

    /**
     * Allows user to save changes to the original file
     */
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
        logData(" user saved image");
    }

    /**
     * Allows user to save image as different file type
     */
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
        logData(" user saved image as");
    }

    /**
     * Allows users to save image as different file type at specific path
     * @param path - path to save as image to
     */
    public void saveImageAs(File path) { //saves existing image as a new image of desired file type

        Alert saveWarning = new Alert(Alert.AlertType.WARNING); //data loss warning
        saveWarning.setTitle("Data Loss Warning");
        saveWarning.setHeaderText("Potential Data Loss");
        String text = "Saving in a different file type may result in image features/data loss. Press 'OK' to continue save.";
        saveWarning.setContentText(text);
        saveWarning.showAndWait();

        this.setFilePath(path);
        this.saveImage();
        logData(" user saved image as");
    }

    /**
     * 'Smart Save' function prompting users to save their work prior to closing the program
     */
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
        logData(" user quit the program");
    }

    /**
     * 'Smart Save' function that prompts users to save their work prior to closing specific tab
     */
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
        logData(" user closed a tab");
    }

    /**
     * Method that logs changes made to the canvas
     * @param content - 'content' as string value of changes being made to the canvas
     */
    public static void logData(String content){ //logs the data to a new file
        try {
            File file = new File(test.toURI());

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("[" + LocalDate.now().toString() +"]" +"  "+ "[" + LocalTime.now().toString() + "] " +  getCurrentTab().getText() + " -" + content + "\r\n");
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the title of the current tab
     * @param title - string value of the current title of the file being modified
     */
    public void setTitle(String title) { //sets the title of the current tab
        this.title = title;
        this.updateTitle(); //updates the title of tab
    }

    /**
     * Gets the current path of the file
     * @return - returns the name of the current path being modified
     */
    public File getPath() { //returns the path of the current file
        return this.path;
    }

    public void updateTitle() { //updates the title of the current tab to the name of the path
        if (this.path != null)
            this.title = this.path.getName();
        else
            this.setText(this.title);
    }

    /**
     * Will undo any recent changes made to the stack
     */
    public void undo() //UNDO CHANGES MADE TO CANVAS (IN THE STACK)
    {
        this.canvas.undo();
    }

    /**
     * Will redo any recent changes that were undone from the stack
     */
    public void redo() //REDO CHANGES MADE TO CANVAS (IN THE STACK)
    {
        this.canvas.redo();
    }

    /**
     * Will allow users to resize the canvas proportionally with given parameters
     * @param x - width value of the new canvas size
     */
    public void resizeCanvas(int x) //resizes the entire canvas proportional with the newly provided canvas width
    {
        this.canvas.setWidth(x); //sets the canvas width to the given int
        this.canvas.setHeight(x / 1.78); //sets the height. i use this to make it proportional. 1920 / 1080 = ~1.78, and 1280 / 720 =~1.78, so this made sense to me
        logData(" user resized the canvas");
    }


}
