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

    public abstract EventType<MouseEvent>[] getEventTypes();
}
