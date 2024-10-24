import java.util.Stack;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;


public abstract class Drawer extends CanvasModifier {
    protected Color color;
    protected double thickness;
    

    /**
     * @param c The canvas object
     * @param ilabel The label that will carry the instructions to the user
     */
    public Drawer(Canvas c, Labeled ilabel) {
        this(c, ilabel, null);
        
        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
    }

    /**
     * @param c The canvas object
     * @param ilabel The label that will carry the instructions to the user
     * @param col (Optional) the color of the object to be drawn
     */
    public Drawer(Canvas c, Labeled ilabel, Color col) {
        super(c, ilabel);

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        thickness = Double.parseDouble(response);
        color = col;
    }

    @Override
    public void startCanvasListener(){
        super.startCanvasListener();

        canvas.getGraphicsContext2D().setStroke(color);
        canvas.getGraphicsContext2D().setLineWidth(thickness);
    }

    /**
     * Lets us draw something "tentatively".
     * i.e. undo's before drawing what it wants to draw
     */
    public void undoBeforeDrawing() {
        PaintTab currTab = PaintTab.getCurrentTab();
        Stack<Canvas> undoStack = currTab.getUndoStack();

        if (undoStack.isEmpty()) {
            System.err.println("Empty undo stack! This shouldn't happen");
            return;
        }

        // otherwise we have something to undo from
        // here we're just undoing without pushing to the redo stack
        currTab.setCanvas(undoStack.pop());
        canvas = currTab.getCanvas();

        // finally refresh everything
        PaintTab.refreshCanvas();
    }

    /**
     * Routine to prepare the canvas to draw something.
     */
    public void predraw() {
        predraw(false);
    }

    /**
     * Routine to prepare the canvas to draw something.
     * @param skipUndo Skips the undo step 
     */
    public void predraw(boolean skipUndo) {
        // we need to pause the listeners
        stopCanvasListener();

        PaintTab currTab = PaintTab.getCurrentTab();

        // we need to undo what has already been drawn
        // unless, of course, this is the first livedraw
        if (!skipUndo) undoBeforeDrawing();

        // then we need to back up the canvas without the drawing on it
        currTab.backup();

        // resize it
        currTab.resize();

        // ok now we can start listening again
        startCanvasListener();
    }

    /**
     * Draws the thing that needs to be drawn.
     */
    protected abstract void draw();
}