import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class Pencil extends Drawer {
    Canvas canvas;
    Double x = null;
    Double y = null;

    public Pencil(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        canvas = c;
    }

    @Override
    public void handle(MouseEvent e) {
        System.out.println(e.isPrimaryButtonDown());
        if (e.isPrimaryButtonDown()) {
            // ... then the mouse has been dragged and is clicked
            // so we put a dot on the canvas
            canvas.getGraphicsContext2D().strokeLine(e.getX(), e.getY(), e.getX(), e.getY());
        }
    }

    @Override
    public EventType<MouseEvent> getEventType() {
        return MouseEvent.MOUSE_DRAGGED;
    }
}