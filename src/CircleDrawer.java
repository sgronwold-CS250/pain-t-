import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class CircleDrawer extends Drawer {

    double[] center = new double[2];
    double radius;

    boolean gotCenter = false;

    public CircleDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);
        
        super.startCanvasListener();

        ilabel.setText("Click the center of the circle");
    }

    @Override
    public void handle(MouseEvent e) {
        if (!gotCenter) {
            center[0] = e.getX();
            center[1] = e.getY();

            gotCenter = true;

            instructionLabel.setText("Click on a point on the circle");
        } else {
            radius = Math.sqrt( Math.pow(center[0] - e.getX(), 2) + Math.pow(center[1] - e.getY(), 2) );

            canvas.getGraphicsContext2D().strokeOval(center[0] - radius, center[1] - radius, 2*radius, 2*radius);

            super.stopCanvasListener();

            instructionLabel.setText("Circle drawn!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }

}
