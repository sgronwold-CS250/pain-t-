import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class SquareDrawer extends Drawer {

    double[] center = new double[2];
    double[] corner = new double[2];

    boolean gotCenter = false;

    public SquareDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        instructionLabel.setText("Click where you want the center to be");

        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        if (!gotCenter) {
            center[0] = e.getX();
            center[1] = e.getY();

            gotCenter = true;
            instructionLabel.setText("Click where you want the corner to be");
        } else {
            // get the corner and deregister this listener
            corner[0] = e.getX();
            corner[1] = e.getY();

            draw();

            // deregister ourselves
            super.stopCanvasListener();

            instructionLabel.setText("Square drawn!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }

    private void draw() {
        // draw a square given the center and corner
        // suppose the center is the origin
        // then the points are:
        // (cx, cy), which is our first corner
        // (cy, -cx)
        // (-cx, -cy)
        // (-cy, cx)

        // normalize the corner w.r.t. the origin
        for (int i = 0; i < corner.length; i++) {
            corner[i] -= center[i];
        }

        // here is our list of points centered around the given center
        double[][] theCorners = new double[][] {
            {corner[0], corner[1], -corner[0], -corner[1]}, // x coords
            {corner[1], -corner[0], -corner[1], corner[0]}  // y coords
        };

        // finally center the corners around the proper center
        for (int i = 0; i < theCorners.length; i++) {
            for (int j = 0; j < theCorners[i].length; j++) {
                theCorners[i][j] += center[i];
            }
        }

        canvas.getGraphicsContext2D().strokePolygon(theCorners[0], theCorners[1], theCorners[0].length);
    }
    
}
