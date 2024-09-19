import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class PolygonDrawer extends Drawer {

    int numSides;

    // need this for last side of polygon
    double[] initialPos = new double[2];

    double[] oldPos = new double[2];
    double[] newPos = new double[2];

    int numPointsCaptured = 0;

    public PolygonDrawer(Canvas c, Labeled ilabel, int nsides) {
        super(c, ilabel);
        numSides = nsides;

        instructionLabel.setText("Click where you want Point #1 to be");

        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        // do we need to get the next point?
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (numPointsCaptured == 0) {                
                // first time only this gets run
                oldPos[0]     = e.getX();
                initialPos[0] = e.getX();

                oldPos[1]     = oldPos[0];
                initialPos[1] = oldPos[1];


                newPos[0] = oldPos[0];
                newPos[1] = oldPos[1];

                predraw(true);
                draw();
            } else {
                // otherwise we actually draw something
                newPos[0] = e.getX();
                newPos[1] = e.getY();

                predraw(true);
                draw();

                oldPos[0] = e.getX();
                oldPos[1] = e.getY();
            }

            numPointsCaptured++;
            System.out.println(numPointsCaptured);

            // if we made all the corners, just autocomplete it
            if (numPointsCaptured == numSides) {
                predraw();
                draw();

                oldPos = initialPos;

                draw();

                super.stopCanvasListener();

                System.out.println("done");

                return;
            }
        }

        if (e.getEventType() == MouseEvent.MOUSE_MOVED && numPointsCaptured != 0) {
            // just draw, don't do anything else
            newPos[0] = e.getX();
            newPos[1] = e.getY();

            predraw();
            draw();
        }


        instructionLabel.setText("Click where you want Point #"+(numPointsCaptured)+" to be");
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED, MouseEvent.MOUSE_MOVED};
    }

    @Override
    public void draw() {
        canvas.getGraphicsContext2D().strokeLine(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
}
