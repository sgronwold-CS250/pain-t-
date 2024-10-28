import java.util.Stack;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class CanvasModifier implements CanvasInterface, EventHandler<MouseEvent> {

    protected Canvas canvas;
    protected Labeled instructionLabel;
    protected static WritableImage clipboard;

    public CanvasModifier(Canvas c, Labeled ilabel) {
        canvas = c;
        instructionLabel = ilabel;

        Main.LOGGER.log(String.format("%s started.", this.getClass().getName()));
    }

    @Deprecated
    public CanvasModifier(Canvas c, Labeled ilabel, Color col) {
        canvas = c;
        instructionLabel = ilabel;

        Main.LOGGER.log(String.format("%s started.", this.getClass().getName()));
    }

    // registers the event handler associated with this class
    public void startCanvasListener() {
        for (EventType<MouseEvent> et : getEventTypes()) {
            canvas.addEventHandler(et, this);
        }

        // not only that we need to make sure we have the correct graphics context
        canvas.getGraphicsContext2D().setImageSmoothing(false);
    }

    // deregisters the event handler associated with this class
    public void stopCanvasListener() {
        for (EventType<MouseEvent> et : getEventTypes()) {
            PaintTab.getCurrentTab().UNSAVED_CHANGES = true;
            canvas.removeEventHandler(et, this);
        }
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

    public abstract EventType<MouseEvent>[] getEventTypes();
}
