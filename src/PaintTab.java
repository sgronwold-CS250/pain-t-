import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Each tab in the Pain (t) program
 */
public class PaintTab extends Canvas implements ChangeListener<Number> {
    private Stack<Canvas> undoStack = new Stack<Canvas>();
    private Stack<Canvas> redoStack = new Stack<Canvas>();

    private Canvas canvas;

    private static GridPane grid;

    boolean UNSAVED_CHANGES = false;
    File currPath;

    static int currTabIndex = 0;
    static ArrayList<PaintTab> tabs = new ArrayList<PaintTab>();
    static ArrayList<Button> buttons = new ArrayList<Button>();

    static final int BUTTON_ROW = 3;
    static final int CANVAS_ROW = 4;

    /**
     * A tab in the Pain(t) program.
     * Uses the default 500 by 500 canvas size.
     */
    public PaintTab() {
        // should have nothing but call to other constructor
        this(500, 500);
    }

    /**
     * A tab in the Pain(t) program.
     * 
     * @param width  Width of the canvas
     * @param height Height of the canvas
     */
    public PaintTab(int width, int height) {
        // should have nothing but call to other constructor
        this(new Canvas(width, height));
    }

    /**
     * A tab in the Pain(t) program.
     * 
     * @param canvas The canvas
     */
    public PaintTab(Canvas c) {
        setCanvas(c);

        add(this);

        Stage stage = (Stage) getGridPane().getScene().getWindow();

        // setting the listener for window resizing
        stage.widthProperty().addListener((ChangeListener<? super Number>) this);
        stage.heightProperty().addListener((ChangeListener<? super Number>) this);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Refreshes the tab buttons.
     * Each tab button is just a number,
     * with brackets surrounding the current tab.
     */
    public static void refreshButtons() {
        // step 1 refresh the button list
        // remove all buttons and start fresh
        buttons.clear();

        for (int i = 0; i < tabs.size(); i++) {
            // make a button for each tab
            Button b;

            if (currTabIndex == i)
                b = new Button("[" + i + "]");
            else
                b = new Button("" + i);

            b.setId("tab" + i);
            b.setOnAction(new TabSwitcher());
            buttons.add(b);
        }

        // step 2 re-add the buttons
        grid = getGridPane();

        // remove the "tab" buttons
        // but we need to add the things to remove to a list
        ArrayList<Node> removeQueue = new ArrayList<Node>();
        for (Node n : grid.getChildren()) {
            if (n instanceof Button) {
                Button b = (Button) n;

                // only the buttons whose ids begin with "tab" should be included here
                if (b.getId().substring(0, 3).equalsIgnoreCase("tab")) {
                    removeQueue.add(n);
                }
            }
        }

        // now we act on our remove queue
        for (Node n : removeQueue) {
            grid.getChildren().remove(n);
        }

        // re-add the buttons
        for (int i = 0; i < buttons.size(); i++) {
            grid.add(buttons.get(i), i, BUTTON_ROW);
        }
    }

    /**
     * Resizes the current tab's canvas
     */
    public void resize() {
        Scene s = getGridPane().getScene();

        double availableCanvasWidth = Math.floor(s.getWidth());

        // this is the height minus the y offset of the canvas; so this is the available
        // height that we have for our canvas
        double availableCanvasHeight = Math.floor(s.getHeight() - getCanvas().getLayoutY());

        // if width or height is nonpositive then we don't need to worry about this
        if (availableCanvasWidth <= 0 || availableCanvasHeight <= 0)
            return;

        // otherwise we have a legit window to work with
        // canvas width does NOT include scaling
        double newScaleX = availableCanvasWidth / getCanvas().getWidth();
        double newScaleY = availableCanvasHeight / getCanvas().getHeight();

        // effective widths and heights
        // heights must be integers
        // so that live draw doesn't screw anything up
        double newScale = Math.min(newScaleX, newScaleY);
        double effectiveCanvasWidth = Math.floor(newScale * getCanvas().getWidth());
        double effectiveCanvasHeight = Math.floor(newScale * getCanvas().getHeight());

        newScaleX = effectiveCanvasWidth / getCanvas().getWidth();
        newScaleY = effectiveCanvasHeight / getCanvas().getHeight();

        if (newScale > 1) {
            newScaleX = Math.floor(newScale);
            newScaleY = Math.floor(newScale);
        } else if (newScale < 1) {
            newScaleX = 1.0/Math.ceil(1/newScale);
            newScaleY = 1.0/Math.ceil(1/newScale);
        }
        
        getCanvas().setScaleX(newScaleX);
        getCanvas().setScaleY(newScaleY);

        // translate the canvas such that it appears aligned within the window
        getCanvas().setTranslateX((newScaleX - 1) / 2 * getCanvas().getWidth());
        getCanvas().setTranslateY((newScaleY - 1) / 2 * getCanvas().getHeight());

        System.out.println("Effective canvas width/height: " + getCanvas().getWidth() * getCanvas().getScaleX() + " by "
                + getCanvas().getHeight() * getCanvas().getScaleY());
    }

    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        resize();
    }

    public static PaintTab getCurrentTab() {
        return tabs.get(currTabIndex);
    }

    /**
     * Adds a new PaintTab to the window's list of tabs.
     * @param pt The new paint tab to be appended to the list
     */
    private static void add(PaintTab pt) {
        tabs.add(pt);

        setCurrentTab(pt);
    }

    /**
     * Make sure the current tab is *actually* the current tab
     */
    public static void refreshCanvas() {
        // just refresh the current tab
        // it'll eventually be set to the currTabIndex anyways
        setCurrentTab(currTabIndex);
        getCurrentTab().resize();
    }

    /**
     * Makes sure the current tab is the ith tab
     * @param i index of new current tab
     */
    public static void setCurrentTab(int i) {
        setCurrentTab(tabs.get(i));
    }

    /**
     * Makes sure the current tab is the tab specified by pt
     * @param pt new current tab
     */
    public static void setCurrentTab(PaintTab pt) {
        int i = tabs.indexOf(pt);

        if (i == -1) {
            System.err.println("This PaintTab object doesn't exist.");
            return;
        }

        grid = getGridPane();

        // remove the current canvas
        // but we need to add the things to remove to a list
        ArrayList<Node> removeQueue = new ArrayList<Node>();
        for (Node n : grid.getChildren()) {
            if (n instanceof Canvas) {
                removeQueue.add(n);
            }
        }

        // now we act on our remove queue
        for (Node n : removeQueue) {
            grid.getChildren().remove(n);
        }

        // now that we've removed the canvas,
        // we can add our new canvas
        currTabIndex = i;

        // add a new canvas
        grid.add(getCurrentTab().getCanvas(), 0, CANVAS_ROW, Main.menuButtons.length, 1);

        // change the action listeners over to the new canvas
        Main.menuActionListener.setCanvas(getCurrentTab().getCanvas());
        Main.menuKeyListener.setCanvas(getCurrentTab().getCanvas());

        // now the buttons need to be refreshed
        refreshButtons();
    }

    /**
     * close out of the current tab
     */
    public static void remove() {
        // this just removes the current paint tab
        remove(getCurrentTab());
    }

    /**
     * close out of the tab specified by pt
     * @param pt the PaintTab object to remove
     */
    public static void remove(PaintTab pt) {
        // make sure that there's actually a tab left
        if (tabs.size() == 1) {
            System.err.println("Cannot remove the only tab remaining!");
            return;
        }

        int i = tabs.indexOf(pt);

        // make sure that pt is actually one of our tabs
        if (i == -1) {
            System.err.println("Cannot remove tab that doesn't exist");
            return;
        }

        // change the current tab to the previous one, if applicable
        if (currTabIndex != 0)
            currTabIndex--;

        // finally, remove the tab and refresh the current tab
        tabs.remove(pt);

        refreshCanvas();
    }

    /**
     * Get the GridPane object of the current PaintTab
     * @return the GridPane object of the current PaintTab
     */
    public static GridPane getGridPane() {
        // if we already have a gridpane stored then we don't need to worry about this
        if (grid != null)
            return grid;

        // this is probably a gridpane
        Parent parent = getCurrentTab().getCanvas().getParent();

        if (parent instanceof GridPane) {
            return (GridPane) parent;
        } else {
            System.err.println("ERROR: This canvas' parent is not a GridPane.");
            System.err.println(parent);
            return null;
        }
    }

    public File getPath() {
        return currPath;
    }

    public void setPath(File f) {
        currPath = f;
    }

    public Stack<Canvas> getUndoStack() {
        return undoStack;
    }

    public Stack<Canvas> getRedoStack() {
        return redoStack;
    }

    public void setCanvas(Canvas c) {
        canvas = c;
    }

    /**
     * Copies the current canvas to the undo stack
     */
    public void backup() {
        pushCanvas(getCanvas());
    }

    /**
     * Copies the canvas to the undo stack
     * @param c the canvas
     */
    public void pushCanvas(Canvas c) {
        Canvas newCanvas = new Canvas(c.getWidth(), c.getHeight());

        // need to disable antialiasing on the canvases
        c.getGraphicsContext2D().setImageSmoothing(false);
        newCanvas.getGraphicsContext2D().setImageSmoothing(false);

        // need to scale the canvas at 100% to snapshot correctly
        double oldScaleX, oldScaleY;
        oldScaleX = c.getScaleX();
        oldScaleY = c.getScaleY();

        c.setScaleX(1);
        c.setScaleY(1);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = c.snapshot(params, null);
        newCanvas.getGraphicsContext2D().drawImage(image, 0, 0);

        // the new canvas is a clone of the old canvas
        // so we'll push the new canvas to the stack
        getUndoStack().push(newCanvas);

        // now we need to change the canvas scale back to what it was before
        c.setScaleX(oldScaleX);
        c.setScaleY(oldScaleY);
    }

    /**
     * Clears the canvas on the current tab.
     */
    public void clear() {
        // make something in the undo stack
        this.backup();

        getCanvas().getGraphicsContext2D().setFill(Color.WHITE);
        getCanvas().getGraphicsContext2D().fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
    }
}
