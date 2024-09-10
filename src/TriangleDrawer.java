import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class TriangleDrawer extends Drawer {

    double[] xCoords = new double[3];
    double[] yCoords = new double[xCoords.length];

    int numPointsCaptured = 0;

    public TriangleDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);

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
        if (numPointsCaptured >= xCoords.length) {
            // draw the triangle
            for (int i = 0; i < xCoords.length; i++) {
                canvas.getGraphicsContext2D().strokeLine(xCoords[i], yCoords[i], xCoords[(i+1)%xCoords.length], yCoords[(i+1)%yCoords.length]);
            }

            // deregister ourselves
            canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

            instructionLabel.setText("Triangle drawn!");
        }
    }
    
}
