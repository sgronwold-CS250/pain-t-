import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class Pencil extends Drawer {
    Canvas canvas;
    Double oldX = null;
    Double oldY = null;

    public Pencil(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        canvas = c;

        super.startCanvasListener();

        instructionLabel.setText("You're in pencil mode now");
    }

    @Override
    public void handle(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            canvas.getGraphicsContext2D().setStroke(color);
            canvas.getGraphicsContext2D().setLineWidth(thickness);


            // if we don't have old coordinates then make them
            if (oldX == null) oldX = e.getX();
            if (oldY == null) oldY = e.getY();

            // ... then the mouse has been dragged and is clicked
            // so we put a dot on the canvas
            canvas.getGraphicsContext2D().strokeLine(oldX, oldY, e.getX(), e.getY());

            // update the former coordinates
            oldX = e.getX();
            oldY = e.getY();
        } else {
            // otherwise we reset the old coordinates
            oldX = null;
            oldY = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_DRAGGED, MouseEvent.MOUSE_CLICKED};
    }
}