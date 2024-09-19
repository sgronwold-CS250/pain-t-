import java.util.Stack;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public abstract class Drawer implements EventHandler<MouseEvent>, CanvasInterface {
    protected Canvas canvas;
    Color color;
    double thickness;
    Labeled instructionLabel;

    public Drawer(Canvas c, Labeled ilabel) {
        this(c, ilabel, null);

        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
    }

    public Drawer(Canvas c, Labeled ilabel, Color col) {
        canvas = c;
        instructionLabel = ilabel;

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        thickness = Double.parseDouble(response);
        color = col;
    }

    public abstract EventType<MouseEvent>[] getEventTypes();

    
    // registers the event handler associated with this class
    public void startCanvasListener() {
        for (EventType<MouseEvent> et: getEventTypes()) {
            canvas.addEventHandler(et, this);
        }

        // not only that we need to make sure we have the correct graphics context
        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.getGraphicsContext2D().setStroke(color);
        canvas.getGraphicsContext2D().setLineWidth(thickness);
    }
    
    // deregisters the event handler associated with this class
    public void stopCanvasListener() {
        for (EventType<MouseEvent> et: getEventTypes()) {
            PaintTab.getCurrentTab().UNSAVED_CHANGES = true;
            canvas.removeEventHandler(et, this);
        }
    }

    // draws something "tentatively",
    // i.e. undo's before drawing what it wants to draw
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

    public void predraw() {
        predraw(false);
    }

    public void predraw(boolean skipUndo) {
        // we need to pause the listeners
        stopCanvasListener();

        PaintTab currTab = PaintTab.getCurrentTab();

        Scene s = currTab.getCanvas().getScene();
        System.out.println(s.getWidth()+", "+s.getHeight());

        // we need to undo what has already been drawn
        // unless, of course, this is the first livedraw
        if (!skipUndo) undoBeforeDrawing();

        // then we need to back up the canvas without the drawing on it
        currTab.backup();

        // ok now we can start listening again
        startCanvasListener();
    }

    // this method is called when you want to actually draw the thing to the canvas
    public abstract void draw();

}