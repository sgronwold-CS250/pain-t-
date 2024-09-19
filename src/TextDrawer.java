import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class TextDrawer extends Drawer {
    String msg;

    double[] mouseCoords = new double[]{0,0};

    public TextDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        TextInputDialog tid = new TextInputDialog("What do you want to say");
        msg = tid.showAndWait().get();

        predraw(true);
        draw();

        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        // refresh mouse coords
        mouseCoords[0] = e.getX();
        mouseCoords[1] = e.getY();

        predraw();
        draw();

        // do we need to stop the listener now?
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            super.stopCanvasListener();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_CLICKED};
    }

    @Override
    public void draw() {
        canvas.getGraphicsContext2D().strokeText(msg, mouseCoords[0], mouseCoords[1]);
    }
}
