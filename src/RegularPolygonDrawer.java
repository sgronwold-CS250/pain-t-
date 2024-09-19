import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class RegularPolygonDrawer extends Drawer {
    int numSides;

    double[] center = new double[2];
    double[] corner = new double[2];

    boolean gotCenter = false;

    public RegularPolygonDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        TextInputDialog tid = new TextInputDialog("How many sides");
        String response = tid.showAndWait().get();

        numSides = Integer.parseInt(response);

        super.startCanvasListener();
    }

    public RegularPolygonDrawer(Canvas c, Labeled ilabel, int nsides) {
        super(c, ilabel);

        instructionLabel.setText("Click where you want the center to be");

        numSides = nsides;

        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        if (!gotCenter) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                center[0] = e.getX();
                center[1] = e.getY();

                gotCenter = true;
                instructionLabel.setText("Click where you want the corner to be");

                predraw(true);
                draw();
            }
        } else {
            // get the corner and deregister this listener
            corner[0] = e.getX();
            corner[1] = e.getY();

            predraw();
            draw();

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                // deregister ourselves
                super.stopCanvasListener();

                instructionLabel.setText("Square drawn!");
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
        // draw a regular polygon given the center and corner
        // suppose the center is the origin

        // here is our list of points
        double[][] theCorners = new double[2][numSides];

        // distance of any given corner from the center of the regular polygon
        double radius = Math.sqrt( Math.pow(corner[0] - center[0], 2) + Math.pow(corner[1] - center[1], 2) );

        double theta0 = Math.atan( (corner[1] - center[1]) / (corner[0] - center[0]) );

        // sometimes theta0 is 180 degrees out of where it should be
        if (corner[0] < center[0]) theta0 += Math.PI;

        for(int i = 0; i < numSides; i++) {
            double theta = theta0 + 2 * Math.PI * i / numSides;

            // x coord
            theCorners[0][i] = radius * Math.cos(theta) + center[0];

            // y coord
            theCorners[1][i] = radius * Math.sin(theta) + center[1];
        }

        canvas.getGraphicsContext2D().strokePolygon(theCorners[0], theCorners[1], numSides);
    }
}
