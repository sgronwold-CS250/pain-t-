import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

/**
 * Pastes the clipboard into the canvas (on user specified location)
 */
public class Paster extends Drawer {
    double x,y;
    boolean firstDraw = true;

    public Paster(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        ilabel.setText("Click upper left corner of where you want to paste");

        startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        // just draw the dang thing
        x = e.getX();
        y = e.getY();

        predraw(firstDraw);
        firstDraw = false;
        draw();

        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            instructionLabel.setText("Done pasting");
            stopCanvasListener();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED, MouseEvent.MOUSE_MOVED};
    }

    @Override
    public void draw() {
        canvas.getGraphicsContext2D().drawImage(clipboard, x, y);
    }
    
}
