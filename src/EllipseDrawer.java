import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class EllipseDrawer extends Drawer {
    // coords
    private double[] left = new double[2];
    private double[] top = new double[2];

    private boolean gotTop = false;

    /**
     * @param c canvas we're eyedropping
     * @param ilabel label to write the instructions to
     */
    public EllipseDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        super.startCanvasListener();

        instructionLabel.setText("Click the top of the ellipse");
    }

    @Override
    public void handle(MouseEvent e) {
        if (!gotTop) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                // get the upper edge
                top[0] = e.getX();
                top[1] = e.getY();

                instructionLabel.setText("Click the leftmost part of the ellipse");

                gotTop = true;

                predraw(true);
                draw();
            }
        } else {
            System.out.println("drawing");
            
            // get the left edge
            left[0] = e.getX();
            left[1] = e.getY();

            predraw();

            draw();

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                super.stopCanvasListener();
                instructionLabel.setText("Ellipse drawn!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED, MouseEvent.MOUSE_MOVED};
    }

    @Override
    public void draw() {
        // make copies of our points
        // so we don't clobber the original
        double leftX = left[0];
        double leftY = left[1];
        double topX = top[0];
        double topY = top[1];

        // idiot proofing: is the left edge actually to the left of the top?
        if (leftX > topX) leftX -= 2*Math.abs(leftX-topX);

        // idiot proofing: is the top edge actually above the left edge?
        if (topY > leftY) topY -= 2*Math.abs(topY-leftY);

        canvas.getGraphicsContext2D().strokeOval(leftX, topY, 2 * Math.abs(topX - leftX), 2 * Math.abs(leftY - topY));
    }
}