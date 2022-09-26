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
import java.io.IOException;
import java.util.Optional;
import static com.example.paint.Paint.mainStage;

public class PaintTabs extends Tab {

    public Pane CanvasPane;
    private static FileChooser chooseFile;
    private String title;
    private File path;
    public static PaintCanvas canvas;
    private ScrollPane sp;
    private StackPane canvasStack;

    public PaintTabs() { //sets the default tab 
        super();
        this.setText("New Tab");
        this.canvas = new PaintCanvas();
        tabStart();
        this.setOnCloseRequest(new EventHandler<Event>()
        {
            @Override
            public void handle(Event arg0)
            {
                forceQuit();
            }
        });
    }



    public PaintTabs(File file) { //sets a new tab on image open 
        super();
        this.path = file;
        this.setText(path.getName());
        this.canvas = new PaintCanvas();
        tabStart();
        this.setOnCloseRequest(new EventHandler<Event>()
        {
            @Override
            public void handle(Event arg0)
            {
                forceQuit();
            }
        });
    }

    private void tabStart() {
        chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("Bitmap", "*.bmp"),
                new FileChooser.ExtensionFilter("Jpeg", "*.jpeg")
        );

        //handles the canvas and the new stack
        this.CanvasPane = new Pane(canvas);
        this.canvasStack = new StackPane();
        this.canvasStack.getChildren().addAll(CanvasPane);


        //handles the scroll pane on the canvas
        this.sp = new ScrollPane(this.canvasStack); //creates a scroll pane
        this.setContent(sp);
        this.sp.setPrefViewportWidth(this.canvas.getWidth() / 2);
        this.sp.setPrefViewportHeight(this.canvas.getHeight() / 2);
    }

    public void setFilePath(File path) {
        this.path = path;
    }

    public File getFilePath() {
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
    }

    public static void newTab() //opens a new
    {
        PaintTabs newTab;
        newTab = new PaintTabs();
        Paint.tabpane.getTabs().add(newTab);
        Paint.tabpane.getSelectionModel().select(newTab);
    }

    public void saveImage() { //saves the image
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

    public void saveImageAs() { //saves image as
        File path = chooseFile.showSaveDialog(mainStage);
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

    public void forceQuit() //prompts the user to save before quitting program
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
                Platform.exit();
                System.exit(0);
            } else {
                //If Cancel button is clicked, program will just exit.
                Platform.exit();
                System.exit(0);
            }
    }


    public void setTitle(String title) { //sets the title of the current tab
        this.title = title;
        this.updateTitle();
    }

    public File getPath() {
        return this.path;
    }

    public void updateTitle() { //updates the title of the current tab to the name of the path
        if (this.path != null)
            this.title = this.path.getName();
        else
            this.setText(this.title);
    }

    public void undo()
    {
        this.canvas.undo();
    }
    public void redo()
    {
        this.canvas.redo();
    }

    public void updateCanvas()
    {
        this.canvas.updateCanvas();
        this.updateTitle();
    }

}
