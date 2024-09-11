import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class PolygonDrawer extends Drawer {

    int numSides;
    double[] xCoords;
    double[] yCoords;

    int numPointsCaptured = 0;

    public PolygonDrawer(Canvas c, Labeled ilabel, int nsides) {
        super(c, ilabel);
        numSides = nsides;

        xCoords = new double[numSides];
        yCoords = new double[numSides];

        instructionLabel.setText("Click where you want Point #1 to be");
    }

    @Override
    public void handle(MouseEvent e) {
        // write the x coords and ycoords of current location
        xCoords[numPointsCaptured] = e.getX();
        yCoords[numPointsCaptured] = e.getY();

        instructionLabel.setText("Click where you want Point #"+(numPointsCaptured+2)+" to be");

        numPointsCaptured++;

        canvas.getGraphicsContext2D().setLineWidth(thickness);
        canvas.getGraphicsContext2D().setStroke(color);
        if (numPointsCaptured >= numSides) {
            // draw the triangle
            for (int i = 0; i < numSides; i++) {
                canvas.getGraphicsContext2D().strokeLine(xCoords[i], yCoords[i], xCoords[(i+1)%xCoords.length], yCoords[(i+1)%yCoords.length]);
            }

            // deregister ourselves
            super.stopCanvasListener();

            instructionLabel.setText(numSides+"-sided polygon drawn!");
        }
    }

    @Override
    public EventType<MouseEvent> getEventType() {
        return MouseEvent.MOUSE_CLICKED;
    }
    
}
