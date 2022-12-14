package com.example.paint;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static com.example.paint.PaintTabs.logData;

/**
 * Malachinski Pain(t) Application - PaintToolBar.java
 * This class file is used to manage the toolbar and its relevant methods. The
 * color, line width, and number of sides (for specific shapes) are managed
 * within this class file.
 **/

public class PaintToolBar extends ToolBar {

    public final static String[] TOOLS = { // all the available tools in the toolbar
            "None", "Square", "Rectangle", "Polygon", "Triangle", "Ellipse", "Circle",
            "Clear Canvas", "Rotate", "Flip Horizontal", "Flip Vertical",
            "Line", "Dashed Line", "Pencil", "Color Dropper", "Eraser", "Copy", "Cut", "Paste", "Move" };

    public final static String[] SAVES = { "Yes", "No" }; // enable or disable autosave
    private static final Integer[] LINE_WIDTH = { 1, 2, 3, 5, 10, 15, 20, 25, 50, 100 }; // hard coded widths
    public static ComboBox<String> toolBox; // creates a combo box to store all the available tools
    private static ComboBox<Integer> widthBox; // creates a combo box to store all the available widths
    private static ComboBox<String> saveBox; // creates a combo box to store the value of auto save
    private static ColorPicker lineColorPicker; // creates the color picker for the line
    private static ColorPicker fillColorPicker; // creates the color picker for the fill of the object
    public static int usingTool; // creates the identifier for the array to observe which tool is being used
    private static int usingWidth; // creates a variable for the line width
    private static TextField sides; // creates an editable text field for the number of sides of the object
    private static int usingSides; // creates a variable to determine how many sides the polygon object will be
    private static int usingSave; // creates a variable to determine if auto save is being used
    private static TextField saveTime; // creates a text variable for the current time for auto save
    private static int currentTime; // creates a variable to determine current time for auto save

    // creates buttons for most common tools in the tool box
    private final Button tool_Line; // tool 11
    private final Button tool_DashedLine; // tool 12
    private final Button tool_Pencil; // tool 13
    private final Button tool_EyeDropper; // tool 14
    private final Button tool_Eraser; // tool 15
    private final Button tool_Copy; // tool 16
    private final Button tool_Cut; // tool 17
    private final Button tool_Paste; // tool 18

    public PaintToolBar() { // sets up the toolbar
        super();
        toolBox = new ComboBox<>(FXCollections.observableArrayList(TOOLS)); // adds all the defined tools to the toolbox
        saveBox = new ComboBox<>(FXCollections.observableArrayList(SAVES));
        widthBox = new ComboBox<>(FXCollections.observableArrayList(LINE_WIDTH)); // adds all the defined widths to box

        // setting all defaults:
        usingWidth = 1; // default line width
        usingTool = 0; // default tool = "none"
        usingSave = 0; // default auto save = "no"
        usingSides = 3; // default number of sides = 3
        currentTime = 45; // default save timer = 15 seconds

        sides = new TextField("3"); // sets the default sides in the editable text field

        lineColorPicker = new ColorPicker(); // creates color picker for the line
        fillColorPicker = new ColorPicker(); // creates color picker for the fill
        lineColorPicker.setValue(Color.BLACK); // default color = black
        toolBox.setValue("None"); // sets the default tool to "none"
        saveBox.setValue("Yes");
        widthBox.setValue(1); // sets the default width to 1
        saveTime = new TextField(Integer.toString(currentTime));
        Label seconds = new Label(" seconds");

        // Creates buttons for most commonly used tools
        tool_Line = new Button();
        tool_DashedLine = new Button();
        tool_Pencil = new Button();
        tool_EyeDropper = new Button();
        tool_Eraser = new Button();
        tool_Copy = new Button();
        tool_Cut = new Button();
        tool_Paste = new Button();

        // adds items to toolbox
        getItems().addAll(new Label("Tools: "), toolBox, new Separator(), sides,
                new Separator(), tool_Line, tool_DashedLine, tool_Pencil, tool_EyeDropper, tool_Eraser, tool_Copy,
                tool_Cut, tool_Paste,
                new Separator(), new Label("Line Width: "), widthBox, new Label("Line Color: "), lineColorPicker,
                new Separator(), new Label("Auto-Save "), saveBox, saveTime, seconds);
        toolBox.setTooltip(new Tooltip("Select the desired tool."));
        widthBox.setTooltip(new Tooltip("Select the desired line width."));
        lineColorPicker.setTooltip(new Tooltip("Select the desired color."));
        saveBox.setTooltip(new Tooltip("Enable or disable auto-save."));

        seconds.setVisible(false); // makes the 'seconds' text box invisible
        saveTime.setVisible(true); // makes the text box visible
        sides.setVisible(false); // makes sides invisible until the proper tool is selected
        sides.setPrefWidth(50);
        saveTime.setPrefWidth(50);

        // sets the tooltips for the most commonly used tools (buttons)
        tool_Line.setTooltip(new Tooltip("Line Tool"));
        tool_DashedLine.setTooltip(new Tooltip("Dashed Line Tool"));
        tool_Pencil.setTooltip(new Tooltip("Pencil Tool"));
        tool_EyeDropper.setTooltip(new Tooltip("EyeDropper Tool"));
        tool_Eraser.setTooltip(new Tooltip("Eraser Tool"));
        tool_Copy.setTooltip(new Tooltip("Copy Tool"));
        tool_Cut.setTooltip(new Tooltip("Cut Tool"));
        tool_Paste.setTooltip(new Tooltip("Paste Tool"));

        // sets the images for the buttons of the most commonly used tools
        try {
            int size = 20;
            tool_Line.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\line.png"), size, size, true, true)));
            tool_DashedLine.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\dashed line.png"), size, size, true, true)));
            tool_Pencil.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\pencil.png"), size, size, true, true)));
            tool_EyeDropper.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\eyedropper.png"), size, size, true, true)));
            tool_Eraser.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\eraser.png"), size, size, true, true)));
            tool_Copy.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\copy.png"), size, size, true, true)));
            tool_Cut.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\cut.png"), size, size, true, true)));
            tool_Paste.setGraphic(new ImageView(
                    new Image(new FileInputStream(Paint.IMAGES + "buttons\\paste.png"), size, size, true, true)));
        } catch (FileNotFoundException e) {
            return;
        }

        // Listeners for the tools
        toolBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> {
            usingTool = newValue.intValue();
            sides.setVisible(TOOLS[usingTool].equals("Polygon")); // enables/disables the text input for the n-gon
            System.out.println("Tool Selected: " + TOOLS[usingTool]);
            logData(" user selected the " + TOOLS[usingTool] + " tool");
        });

        saveBox.getSelectionModel().selectedIndexProperty().addListener((observable, value, newValue) -> {
            usingSave = newValue.intValue();
            saveTime.setVisible(SAVES[usingSave].equals("Yes"));
            seconds.setVisible(SAVES[usingSave].equals("Yes"));
        });

        sides.textProperty().addListener((observable, value, newValue) -> { // parses the text input for sides

            if (Integer.parseInt(newValue) >= 3)
                usingSides = Integer.parseInt(newValue);
            else {
                sides.setText("1");
            }
        });

        saveTime.textProperty().addListener((observable, value, newValue) -> { // sets the auto-save
            if (Integer.parseInt(newValue) >= 1)
                currentTime = Integer.parseInt(newValue);
            else {
                saveTime.setText("1");
            }
            Paint.getCurrentTab().updateSaveTimer();
        });

        widthBox.setOnAction((ActionEvent e) -> { // sets new line width as selected width
            usingWidth = widthBox.getValue();
            System.out.println("Width Selected: " + usingWidth); // print statement for debugging purposes
            logData(" user changed the width to " + usingWidth);
        });

        // All button tool listeners
        tool_Line.setOnAction((ActionEvent e) -> {
            usingTool = 11;// line tool
        });

        tool_DashedLine.setOnAction((ActionEvent e) -> {
            usingTool = 12;// dashed line tool
        });

        tool_Pencil.setOnAction((ActionEvent e) -> {
            usingTool = 13;// pencil tool
        });

        tool_EyeDropper.setOnAction((ActionEvent e) -> {
            usingTool = 14;// eyedropper tool
        });

        tool_Eraser.setOnAction((ActionEvent e) -> {
            usingTool = 15;// eraser tool
        });

        tool_Copy.setOnAction((ActionEvent e) -> {
            usingTool = 16;// copy tool
        });

        tool_Cut.setOnAction((ActionEvent e) -> {
            usingTool = 17;// cut tool
        });

        tool_Paste.setOnAction((ActionEvent e) -> {
            usingTool = 18;// paste tool
        });
    }

    /**
     * Gets the current tool being used
     * 
     * @return - returns the string value of the current tool being used
     */
    public static String getTool() // returns current tool
    {
        return TOOLS[usingTool];
    }

    /**
     * Gets the current save time
     * 
     * @return - returns the string value of the current save time
     */
    public static String getSave() // returns the save time
    {
        return SAVES[usingSave];
    }

    /**
     * Gets the current color being used for the line
     * 
     * @return - returns the color value of the current line
     */
    public static Color getLineColor() { // gets the color of the line
        return lineColorPicker.getValue();
    }

    /**
     * Gets the current color being used for the fill
     * 
     * @return - returns the color value of the current fill
     */
    public static Color getFillColor() { // gets the color of the fill object
        return fillColorPicker.getValue();
    }

    /**
     * Sets the color for the current line
     * 
     * @param color - sets the color value of the current line
     */
    public static void setLineColor(Color color) { // sets the color of the line
        lineColorPicker.setValue(color);
    }

    /**
     * Sets the color for the current fill
     * 
     * @param color - sets the color value for the current fill
     */
    public static void setFillColor(Color color) { // sets the fill color of the object
        fillColorPicker.setValue(color);
    }

    /**
     * Gets the width of the current line
     * 
     * @return - returns int value of current line width
     */
    public static int getLineWidth() { // returns the width of the current object
        return usingWidth;
    }

    /**
     * Gets the current number of sides of shape
     * 
     * @return - returns int value of the current shape's sides
     */
    public static int getUsingSides() { // returns the current number of sides for the object
        return usingSides;
    }

    /**
     * Gets the current time for auto-save
     * 
     * @return - returns int value of the current time for auto-save timer
     */
    public static int getSaveTimer() {
        return currentTime;
    }
}
