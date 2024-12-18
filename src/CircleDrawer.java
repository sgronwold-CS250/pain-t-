import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class CircleDrawer extends Drawer {

    private double[] center = new double[2];
    private double radius;
    private boolean gotCenter = false;

    /**
     * @param c The canvas we're drawing the circle on.
     * @param ilabel The object we write the instructions to.
     */
    public CircleDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);
        
        super.startCanvasListener();

        ilabel.setText("Click the center of the circle");
    }

    @Override
    /**
     * Callback that handles the MouseEvents specified by getEventTypes().
     * @param e The event that triggered this callback.
     */
    public void handle(MouseEvent e) {
        if (!gotCenter) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                center[0] = e.getX();
                center[1] = e.getY();

                gotCenter = true;

                instructionLabel.setText("Click on a point on the circle");

                predraw(true);
                draw();
            }
        } else {
            radius = Math.sqrt( Math.pow(center[0] - e.getX(), 2) + Math.pow(center[1] - e.getY(), 2) );

            predraw();
            draw();

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                super.stopCanvasListener();
                instructionLabel.setText("Circle drawn!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    /**
     * @return The EventType's that trigger the callback.
     */
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED, MouseEvent.MOUSE_MOVED};
    }

    @Override
    /**
     * Draws a circle with specified center and radius.
     */
    protected void draw() {
        canvas.getGraphicsContext2D().strokeOval(center[0] - radius, center[1] - radius, 2*radius, 2*radius);
    }

}
