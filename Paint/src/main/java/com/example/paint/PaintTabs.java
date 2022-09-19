package com.example.paint;


import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
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

import static com.example.paint.Paint.white;

public class PaintTabs extends Tab {

    public Pane CanvasPane;
    private static FileChooser chooseFile;
    private double scale;
    private String title;
    private File path;
    private PaintCanvas canvas;
    private ScrollPane sp;
    private StackPane canvasStack;



    public PaintTabs(){
        super();
        this.setText("New Tab");
        this.canvas = new PaintCanvas();
        setUp();
    }

    public PaintTabs(File file){
        super();
        this.path = file;
        this.setText(path.getName());
        this.canvas = new PaintCanvas();
        setUp();
    }

    private void setUp(){
        this.scale = 1;

        chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("Bitmap", "*.bmp")
        );

        this.CanvasPane = new Pane(canvas);
        this.canvasStack = new StackPane();
        this.canvasStack.getChildren().addAll(CanvasPane);
        this.sp = new ScrollPane(this.canvasStack);
        this.setContent(sp);
        this.sp.setPrefViewportWidth(this.canvas.getWidth()/2);
        this.sp.setPrefViewportHeight(this.canvas.getHeight()/2);
    }

    public void setFilePath(File path)
    {
        this.path = path;
    }

    public File getFilePath()
    {
        return this.path;
    }

    public static void openImage() {
        File path = chooseFile.showOpenDialog(Paint.mainStage);
        PaintTabs temp;
        if (path == null)
            temp = new PaintTabs();
        else
            temp = new PaintTabs(path);

        temp.canvas.drawImage(path);
        Paint.tabpane.getTabs().add(temp);
        Paint.tabpane.getSelectionModel().select(temp);
    }

    public static void newTab()
    {
        PaintTabs temp;
        temp = new PaintTabs();
        Paint.tabpane.getTabs().add(temp);
        Paint.tabpane.getSelectionModel().select(temp);
    }


    public void saveImage()
    {
        Image im = this.canvas.getRegion(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        try{
            if(this.path != null){
                ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", this.path);
                this.setTitle(this.getFilePath().getName());
            }
        }catch(IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public void saveImageAs()
    {
        File path = chooseFile.showSaveDialog(Paint.mainStage);
        this.setFilePath(path);
        this.saveImage();
    }

    public void quitProgram() //TODO: finish this function
    {
    if(this.path != null)
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
    }

    public PaintCanvas getCanvas()
    {
        return this.canvas;
    }

    public double getCanvasHeight()
    {
        return this.canvas.getHeight();
    }

    public double getCanvasWidth()
    {
        return this.canvas.getWidth();
    }

    public void drawImageAt(Image im, double x, double y)
    {
        this.canvas.drawImageAt(im, x, y);
    }

    public void setTitle(String title)
    {
        this.title = title;
        this.updateTitle();
    }

    public File getPath()
    {
        return this.path;
    }

    public void updateTitle()
    {
        if(this.path != null)
            this.title = this.path.getName();
        else
            this.setText(this.title);
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public double getScale(){
        return this.scale;
    }

    public void resetScale()
    {
        this.setScale(1);
    }

    public void updateScale()
    {
        this.CanvasPane.setScaleX(this.getScale());
        this.CanvasPane.setScaleY(this.getScale());
        this.CanvasPane.setPrefSize(this.canvas.getWidth()*this.getScale()* 2, this.canvas.getHeight()*this.getScale()*2);
        PaintToolBar.setZoomLabel(this.getScale()); //?
    }


}
