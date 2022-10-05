package com.example.paint;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Malachinski Pain(t) Application - PaintMenuBar.java
 * This class file is used to manage the menu bar and all the relevant menu bar events.
 *
 **/

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

        //'New' menu item. Allows users to create a new tab and a new project.
        MenuItem New = new MenuItem("New");
        New.setMnemonicParsing(true);
        New.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        New.setOnAction((ActionEvent event) -> {
            PaintTabs.newTab();
        });

        //'Open' menu item. Allows users to open a picture to the current project.
        MenuItem Open = new MenuItem("Open");
        Open.setMnemonicParsing(true);
        Open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)); //sets hotkey CTRL + O --> Open Program
        Open.setOnAction((ActionEvent event) -> {
            PaintTabs.openImage();
        });

        //'Save' menu item. Allows users to save current project.
        MenuItem Save = new MenuItem("Save"); //Creates menu option to save current user project
        Save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)); //sets hotkey CTRL + S --> Save
        Save.setMnemonicParsing(true);
        Save.setOnAction((ActionEvent event) -> {
            if (Paint.getCurrentTab().getFilePath() == null)
                Paint.getCurrentTab().saveImageAs();
            else
                Paint.getCurrentTab().saveImage();
        });

        //'Save as' menu item. Allows users to save current project as a different file.
        MenuItem SaveAs = new MenuItem("Save as..."); //Creates menu option to save current user project as different file
        SaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)); //sets hotkey CTRL + Shift + S --> Save
        SaveAs.setOnAction((ActionEvent event) -> {
            try {
                Paint.getCurrentTab().saveImageAs();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        });


        //'Exit' menu item. Allows users to exit current project after prompting them to save.
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem Exit = new MenuItem("Exit", null);
        Exit.setMnemonicParsing(
                true);
        Exit.setAccelerator(
                new KeyCodeCombination(KeyCode.ESCAPE)); //sets hotkey ESC --> Ends program
        Exit.setOnAction((ActionEvent event) -> {
            try {
                Paint.getCurrentTab().quitProgram();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        });

        //Creates the About section in the Help menu
        MenuItem About = new Menu("About");
        About.setMnemonicParsing(
                true);
        About.setAccelerator(
                new KeyCodeCombination(KeyCode.F1, KeyCombination.SHIFT_DOWN)); //sets hotkey Shift + F1 --> opens about tab
        About.setOnAction(e -> {
            Alert aboutPaint = new Alert(Alert.AlertType.INFORMATION);
            aboutPaint.setTitle("About");
            aboutPaint.setHeaderText("About Pain(t) v1.0.1");
            String text = "Pain(t) - v1.0.1 is a JavaFX image handling project created by Charlie Malachinski. " +
                    "This program currently allows users to upload, save and save as images. These images can be drawn upon using shapes and saved with the new markings. " +
                    "\n" + "\n" +
                    "More features will come in the future.";
            aboutPaint.setContentText(text);
            aboutPaint.showAndWait();
        });

        //'Help' menu item. Allows users to browse a 'Q&A' section about the application.
        MenuItem helpOption = new Menu("Help");
        helpOption.setMnemonicParsing(
                true);
        helpOption.setAccelerator(
                new KeyCodeCombination(KeyCode.H, KeyCombination.SHIFT_DOWN)); //sets hotkey Shift + H --> opens help tab
        helpOption.setOnAction(e -> {
            Alert helpPaint = new Alert(Alert.AlertType.INFORMATION);
            helpPaint.setTitle("Help");
            helpPaint.setHeaderText("Q&A - Help for Pain(t)");
            String text =
                    "Q: How do I open an image?\n" +
                            "A: File -> Open -> Select desired image.\n" +
                            "\n" +
                            "Q: How do I change the size of the line?\n" +
                            "A: Select desired width from the 'Line Width' drop down.\n" +
                            "\n" +
                            "Q: What are the keyboard shortcuts for the application?\n" +
                            "A: The following are the shortcuts used by the application: \n" +
                            "   'Ctrl + O' is used to open an image\n" +
                            "   'Ctrl + S is used to save an image\n" +
                            "   'Ctrl + Shift + S is used to save image as\n" +
                            "   'Esc' is used to exit the program.\n";
            helpPaint.setContentText(text);
            helpPaint.showAndWait();
        });

        //"Undo" menu item. Allows users to undo their last change.
        MenuItem Undo = new MenuItem("Undo");
        Undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        Undo.setOnAction((ActionEvent event) -> {
            Paint.getCurrentTab().undo();
        });

        //"Redo" menu item. Allows users to redo their last change.
        MenuItem Redo = new MenuItem("Redo");
        Redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        Redo.setOnAction((ActionEvent event) -> {
            Paint.getCurrentTab().redo();
        });

        //"Resize" menu item. Allows users to resize the canvas for larger drawing.
        MenuItem Resize = new MenuItem("Resize"); //THIS WORKS: however it is not the most practical code. I can change this later to make it better.
        Resize.setOnAction((ActionEvent event ) -> {

            TextInputDialog input = new TextInputDialog("1280"); //sets default width to 1280
            input.setContentText("New Width: ");
            input.setHeaderText("Resize Canvas");
            Label label = new Label("");
            input.showAndWait();
            label.setText(input.getEditor().getText());

            try{ //tries to resize canvas with given parameter
                Integer.parseInt(label.getText());
                PaintTabs.resizeCanvas(Integer.valueOf(label.getText()));
            }
            catch (Exception e) { //catches exception and prompts user to try again
                input = new TextInputDialog("1280");
                input.setContentText("New Width: ");
                input.setHeaderText("INVALID: Enter Valid Width");
                input.showAndWait();
                label.setText(input.getEditor().getText());
                Integer.parseInt(label.getText()); //i need to change this but idk what to change it to
                PaintTabs.resizeCanvas(Integer.valueOf(label.getText()));

            }
        });


        //This section adds all the File options to the menu bar
        File.getItems().addAll(New, Open, Save, SaveAs, separator, Exit);
        //This section adds all the Edit options
        Edit.getItems().addAll(Undo, Redo, Resize);
        //This section adds the About option under Help
        Help.getItems().addAll(About, helpOption);
    }

}