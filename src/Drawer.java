import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public abstract class Drawer implements EventHandler<MouseEvent>, CanvasInterface {
    Canvas canvas;
    Color color;
    double thickness;
    Labeled instructionLabel;

    public Drawer(Canvas c, Labeled ilabel) {
        this(c, ilabel, null);

        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
        canvas.getGraphicsContext2D().setStroke(color);
    }

    public Drawer(Canvas c, Labeled ilabel, Color col) {
        canvas = c;
        instructionLabel = ilabel;

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        thickness = Double.parseDouble(response);
        canvas.getGraphicsContext2D().setLineWidth(thickness);

        color = col;
        canvas.getGraphicsContext2D().setStroke(color);
    }

    public abstract EventType<MouseEvent>[] getEventTypes();

    
    // registers the event handler associated with this class
    public void startCanvasListener() {
        for (EventType<MouseEvent> et: getEventTypes()) {
            canvas.addEventHandler(et, this);
        }
    }
    
    // deregisters the event handler associated with this class
    public void stopCanvasListener() {
        for (EventType<MouseEvent> et: getEventTypes()) {
            Main.UNSAVED_CHANGES = true;
            canvas.removeEventHandler(et, this);
        }
    }
}
