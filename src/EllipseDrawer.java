import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class EllipseDrawer extends Drawer {
    // coords
    double[] left = new double[2];
    double[] top = new double[2];

    boolean gotTop = false;

    public EllipseDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        super.startCanvasListener();

        instructionLabel.setText("Click the top of the ellipse");
    }

    @Override
    public void handle(MouseEvent e) {
        if (!gotTop) {
            // get the upper edge
            top[0] = e.getX();
            top[1] = e.getY();

            instructionLabel.setText("Click the leftmost part of the ellipse");

            gotTop = true;
        } else {
            // get the left edge
            left[0] = e.getX();
            left[1] = e.getY();

            // idiot proofing: is the left edge actually to the left of the top?
            if (left[0] > top[0]) left[0] -= 2*Math.abs(left[0]-top[0]);

            // idiot proofing: is the top edge actually above the left edge?
            if (top[1] > left[1]) top[1] -= 2*Math.abs(top[1]-left[1]);

            canvas.getGraphicsContext2D().strokeOval(left[0], top[1], 2 * Math.abs(top[0] - left[0]), 2 * Math.abs(left[1] - top[1]));

            instructionLabel.setText("Ellipse drawn!");

            super.stopCanvasListener();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }
}