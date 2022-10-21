package com.example.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

import java.util.Optional;
import java.util.Stack;

import static com.example.paint.Paint.mainStage;
import static com.example.paint.PaintTabs.canvasStack;
import static com.example.paint.PaintTabs.logData;

/**
 * Malachinski Pain(t) Application - PaintCanvas.java
 * This class file is used to manage the changes made on the canvas. Tool selection and their methods occurs here.
 *
 **/
public class PaintCanvas extends PaintDraw {
    private double x, y, x1, y1; //variables used for drawing
    private Image image; //image object used for undo/redo stack
    private Stack<Image> undo; //stack used for 'undo' changes
    private Stack<Image> redo; //stack used for 'redo' changes

    private ImageView imagev1 = new ImageView();
    private ImageView imagev2 = new ImageView();

    private Translate test = new Translate();

    public PaintCanvas() {
        super();
        //set defaults for undo/redo stack
        this.image = null; //default image is null
        this.undo = new Stack<>(); //new stack for undo
        this.redo = new Stack<>(); //new stack for redo

        //set default background size & color
        this.setWidth(1280); //default width is 1280
        this.setHeight(720); //default height is 720
        this.setShapeFill(true); //default fill = yes
        this.setFillColor(Color.WHITE); //default fill = white
        this.setLineColor(Color.WHITE); //default line = white
        this.rectTool(0,0,this.getWidth(), this.getHeight()); //draws a background for the default
        this.undo.push(this.getRegion(0, 0, this.getWidth(), this.getHeight())); //pushes undo on create

        setOnMouseMoved(e -> { //when mouse is moved at all on canvas
            switch (PaintToolBar.getTool()) {
                case ("Line"), ("Dashed Line"), ("Rectangle"), ("Pencil"), ("Ellipse"), ("Square"), ("Triangle"), ("Circle"), ("None"), ("Eraser"), ("Copy"), ("Cut") ->
                        setCursor(Cursor.DEFAULT); //cursor is just default
                case ("Clear Canvas"), ("Paste") -> setCursor(Cursor.OPEN_HAND); //cursor is hand, couldn't think of better cursor

                case ("Color Dropper") -> setCursor(Cursor.CROSSHAIR); //cursor is crosshair, thought it would be p cool
            }
        });

        setOnMousePressed(e -> { //when mouse is initially held down
            x = e.getX();
            y = e.getY();
            this.setLineColor(PaintToolBar.getLineColor()); //gets the desired line color from PaintToolBar class
            this.setLineWidth(PaintToolBar.getLineWidth()); //gets the desired line width from PaintToolBar class
            this.setFillColor(PaintToolBar.getFillColor()); //gets the desired fill color from PaintToolBar class
            switch (PaintToolBar.getTool()) {


                case ("Line"): //draws a line at the beginning coordinates
                    this.lineDashes(0);
                    this.lineTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Dashed Line"): //draws a dashed line at the beginning coordinates
                    this.dashedLineTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Pencil"): //draws the beginning pencil mark at the beginning coordinates
                    this.lineDashes(0);
                    this.drawPencilStart(x, y);
                    this.updateCanvas();
                    break;

                case ("Square"): //draws a square at the beginning coordinates
                    this.lineDashes(0);
                    this.squareTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Rectangle"):  //draws a rectangle at the beginning coordinates
                    this.lineDashes(0);
                    this.rectTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Polygon"):  //draws a rectangle at the beginning coordinates
                    this.polygonTool(x, y, x, y, 5);
                    this.updateCanvas();
                    break;

                case("Triangle"):  //draws a triangle at the beginning coordinates
                    this.lineDashes(0);
                    this.triangleTool(x, y, x, y, 3); //hard coded 3 for the triangle to work
                    this.updateCanvas();
                    break;

                case ("Ellipse"):  //draws an ellipse at the beginning coordinates
                    this.lineDashes(0);
                    this.ellipseTool(x, y, x, y); //draws an ellipse at the beginning coordinates
                    this.updateCanvas();
                    break;

                case ("Circle"):  //draws a circle at the beginning coordinates
                    this.lineDashes(0);
                    this.circleTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"):  //sets the line color to the pixel selected from the eyeDropper function
                    this.lineDashes(0);
                    PaintToolBar.setLineColor(this.eyeDropper(x, y)); //changes cursor for color selector
                    break;

                case ("Eraser"):  //erases (draws a white line) to the selected pixel
                    this.lineDashes(0);
                    this.setLineColor(Color.WHITE);
                    this.drawEraserStart(x, y); //draws the beginning eraser mark at the beginning coordinates
                    this.updateCanvas();
                    break;

                case("Copy"): //copies a part of the canvas at the desired coordinates
                    this.lineDashes(5);
                    this.setLineWidth(2);
                    this.setLineColor(Color.GREEN);
                    this.rectTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case ("Cut"): //cuts a part of the canvas at the desired coordinates
                    this.setLineWidth(2);
                    this.setLineColor(Color.BLUE);
                    this.rectTool(x, y, x, y);
                    this.updateCanvas();
                    break;

                case("Paste"): //pastes the copied / cut part of the canvas at the desired coordinates
                    this.lineDashes(0);
                    try{
                        if (image != null)
                        {
                            this.imagev1.setImage(image);
                            this.imagev1.setX(e.getX());
                            this.imagev1.setY(e.getY());
                            canvasStack.getChildren().add(this.imagev1);
                        }

                    }catch(Exception exception){
                        System.out.println(exception);
                    }
                    break;

                case ("Move"):
                    if(image != null)
                    {
                        this.imagev2.setImage(image);
                        canvasStack.getChildren().remove(this.imagev1);
                        canvasStack.getChildren().remove(this.imagev2);

                        this.test.setX(e.getX());
                        this.test.setY(e.getY());

                        this.imagev2.getTransforms().add(test);
                        canvasStack.getChildren().add(this.imagev2);

                        this.updateCanvas();
                    }

//                    canvasStack.getChildren().add(imagev1);
//                    try{
//                        this.drawImageAt(image, e.getX(), e.getY());
//                    } catch(Exception r)
//                    {
//                        System.out.println(r);
//                    }
                    this.updateCanvas();
                    break;

                case ("Clear Canvas"): //clears the canvas on click
                    //add onclick listener here
                        Alert clear = new Alert(Alert.AlertType.CONFIRMATION);
                        clear.setTitle("Clear Canvas?");
                        String text = "Would you like to clear the canvas? (Changes will not be saved.)";
                        clear.setContentText(text);
                        Optional<ButtonType> show = clear.showAndWait();
                        if ((show.isPresent()) && (show.get() == ButtonType.OK)) {
                            try {
                                this.clearCanvas();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            //After project is saved, program will exit.
                            this.updateCanvas();
                        } else {
                            this.updateCanvas();
                        }
                        break;

                case ("Rotate"): //rotates 90 degrees
                    if (image != null)
                    {
                            imagev1.setImage(null);
                            canvasStack.getChildren().remove(imagev1);
                            this.updateCanvas();
                            TextInputDialog input = new TextInputDialog("0"); //sets default width to 1280
                            input.setContentText("New Angle: ");
                            input.setHeaderText("Rotate Image");
                            Label label = new Label("");
                            input.showAndWait();
                            label.setText(input.getEditor().getText());

                            try{ //tries to resize canvas with given parameter
                                Integer.parseInt(label.getText());
                                imagev1.setRotate(Integer.parseInt(label.getText()));
                            }
                            catch (Exception ex) { //catches exception and prompts user to try again
                                input = new TextInputDialog("0");
                                input.setContentText("New Angle: ");
                                input.setHeaderText("INVALID: Enter Valid Angle");
                                input.showAndWait();
                                label.setText(input.getEditor().getText());
                                imagev1.setRotate(Integer.parseInt(label.getText()));
                            }
                        this.updateCanvas();
//                        canvasStack.getChildren().remove(imagev1);
                        imagev1.setImage(image);
                        canvasStack.getChildren().add(imagev1);
                    }
                    else {
                        this.rotateImage();
                    }
                    break;

                case ("Flip Horizontal"):
                    this.flipImageY();
                    break;

                case ("Flip Vertical"):
                    this.flipImageX();
                    break;

                case ("None"):
                    //does nothing
                    break;
            }
        });

        //when mouse is dragged on canvas
        setOnMouseDragged(e -> {
            x1 = e.getX();
            y1 = e.getY();


            switch (PaintToolBar.getTool()) {
                case ("Line"):

                case ("Circle"):

                case ("Ellipse"):

                case ("Polygon"):

                case ("Triangle"):

                case ("Rectangle"):

                case ("Square"):
                    //does nothing during drag event
                    break;

                case ("Pencil"): //draws a pencil stroke to the end coordinates
                    this.lineDashes(0);
                    this.drawPencilEnd(x1, y1);
                    this.updateCanvas();
                    break;

                case ("Color Dropper"): //sets the line color to the pixel selected from the eyeDropper function
                    this.lineDashes(0);
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();
                    break;

                case ("Eraser"):
                    this.lineDashes(0);
                    this.drawEraserEnd(x1, y1); //draws a pencil stroke to the end coordinates
                    this.updateCanvas();
                    break;

                case ("Copy"):
                    this.lineDashes(5);
                    undo(); //this is what gets rid of the image
                    this.rectTool(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Cut"):
                    this.undo();
                    this.rectTool(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;


                case ("Paste"):
                    this.lineDashes(0);
                    this.undo();
                    canvasStack.getChildren().remove(imagev1);
                    try{
                        if (image != null)
                        {
                            imagev1.setImage(image);
                            imagev1.setX(e.getX());
                            imagev1.setY(e.getY());
                            canvasStack.getChildren().add(imagev1);
                        }
                    }catch(Exception exception){
                        System.out.println(exception);
                    }
                    this.updateCanvas();
                    break;

                case ("Move"):

                    if(imagev1 != null)
                    {
                        this.imagev2.setImage(image);
                        canvasStack.getChildren().remove(this.imagev1);
                        canvasStack.getChildren().remove(this.imagev2);

                        this.test.setX(e.getX());
                        this.test.setY(e.getY());

                        this.imagev2.getTransforms().add(test);
                        canvasStack.getChildren().add(this.imagev2);

                        this.updateCanvas();
                    }

//                    try{
//                        this.drawImageAt(image, e.getX(), e.getY());
//                    } catch (Exception r) {
//                        System.out.println(r);
//                    }
                    this.updateCanvas();
                    break;

                case ("None"):
                    //does nothing
                    break;
            }
        });

        //when mouse is released, the following graphics are drawn
        setOnMouseReleased(e -> {
            x1 = e.getX();
            y1 = e.getY();

            switch (PaintToolBar.getTool()) {
                case ("Line"): //draws a normal line using the coordinates
                    this.lineDashes(0);
                    this.lineTool(x, y, x1, y1); //draws a line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    logData(" user drew a line to the canvas");
                    break;

                case ("Dashed Line"): //draws a dashed line using the coordinates
                    this.lineDashes(0);
                    this.dashedLineTool(x, y, x1, y1); //draws a dashed line from starting coordinates to new ending coordinates
                    this.updateCanvas();
                    logData(" user drew a dashed line to the canvas");
                    break;
                case ("Pencil"):
                    //does nothing, already drawn
                    break;

                case ("Square"): //draws a square using the coordinates
                    this.lineDashes(0);
                    this.squareTool(x, y, x1, y1); //draws a square from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    logData(" user drew a square to the canvas");
                    break;

                case ("Rectangle"): //draws a rectangle using the coordinates
                    this.lineDashes(0);
                    this.rectTool(x, y, x1, y1); //draws a rectangle from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    logData(" user drew a rectangle to the canvas");
                    break;

                case ("Polygon"): //draws a polygon using the coordinates and the selected sides
                    this.polygonTool(x, y, e.getX(), e.getY(), PaintToolBar.getUsingSides());
                    this.updateCanvas();
                    logData(" user drew a polygon to the canvas");
                    break;

                case ("Triangle"): //draws a triangle using the coordinates
                    this.lineDashes(0);
                    this.triangleTool(x, y, e.getX(), e.getY(), 3);
                    this.updateCanvas();
                    logData(" user drew a triangle to the canvas");
                    break;

                case ("Ellipse"): //draws an ellipse using the coordinates
                    this.lineDashes(0);
                    this.ellipseTool(x, y, x1, y1); //draws an ellipse from the starting coordinates to the new ending coordinates
                    this.updateCanvas();
                    logData(" user drew an ellipse to the canvas");
                    break;

                case ("Circle"): //draws a circle using the coordinates
                    this.lineDashes(0);
                    this.circleTool(x, y, x1, y1);
                    this.updateCanvas();
                    logData(" user drew a circle to the canvas");
                    break;

                case ("Color Dropper"): //uses the eye drop function to get color at exact desired pixel
                    this.lineDashes(0);
                    setCursor(Cursor.CROSSHAIR); //changes cursor for color selector
                    PaintToolBar.setLineColor(this.eyeDropper(x, y));
                    this.updateCanvas();

                    break;

                case ("Eraser"):
                    //does nothing
                    break;

                case ("Copy"): //copies a piece of the canvas at the given coordinates
                    this.lineTool(0, 0, 0, 0);
                    this.lineDashes(0);
                    this.undo(); //this is what gets rid of the image!
                    this.image = this.getRegion(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Cut"): //cuts a piece of the canvas at the given coordinates
                    this.undo();
                    this.image = this.getRegion(x, y, e.getX(), e.getY());
                    this.setLineWidth(0);
                    this.setShapeFill(true);
                    this.setFillColor(Color.WHITE);
                    this.setLineColor(PaintToolBar.getFillColor());
                    this.rectTool(x, y, e.getX(), e.getY());
                    this.updateCanvas();
                    break;

                case ("Move"):
                    if(imagev1 != null)
                    {
                        this.imagev2.setImage(image);
                        canvasStack.getChildren().remove(this.imagev1);
                        canvasStack.getChildren().remove(this.imagev2);
                        this.test.setX(e.getX());
                        this.test.setY(e.getY());
                        this.imagev2.getTransforms().add(test);
                        canvasStack.getChildren().add(this.imagev2);
                        System.out.println(this.imagev2.getTranslateX());
                        System.out.println(e.getX());
                        System.out.println(imagev2.getScaleX());
                        System.out.println("image height " + image.getHeight());
                        System.out.println("image view height "  + imagev2.getFitHeight());

                        this.updateCanvas();


                    }




//                    if (this.image != null)
//                    {
//                        this.drawImageAt(this.image, e.getX(), e.getY());
//                    }
//                    canvasStack.getChildren().remove(imagev1);
                    break;

                case ("None"):
                    System.out.println("Nothing done");
                    break;
            }
            this.updateCanvas();
        });
    }

    public void undo() //this is broken ?
    {
        Image img = undo.pop();
        if(!undo.empty()){
            redo.push(img);
            this.drawImage(undo.peek());
        }
        else{
            this.drawImage(img);
            undo.push(img);
        }
    }

    public void redo() //this is broken too?
    {
        if(!redo.empty()){
            Image im = redo.pop();
            undo.push(im);
            this.drawImage(im);
        }
    }

    public void updateCanvas() //updates the canvas on the stack
    {
        undo.push(this.getRegion(0, 0, this.getWidth(), this.getHeight()));
        redo.clear();
    }
}
