import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class RectangleDrawer extends Drawer {
    double[] corner1 = new double[2];
    double[] corner2 = new double[2];

    boolean corner1Drawn = false;

    public RectangleDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);
        
        super.startCanvasListener();
        
        ilabel.setText("Click on a corner of the rectangle");
    }

    @Override
    public void handle(MouseEvent e) {
        if (!corner1Drawn) {
            corner1[0] = e.getX();
            corner1[1] = e.getY();

            corner1Drawn = true;

            instructionLabel.setText("Click on the opposite corner of the rectangle");
        } else {
            corner2[0] = e.getX();
            corner2[1] = e.getY();

            instructionLabel.setText("Rectangle drawn!");
            super.stopCanvasListener();

            // x, y, width, height
            canvas.getGraphicsContext2D().strokeRect(Math.min(corner1[0], corner2[0]), Math.min(corner2[0], corner2[1]), Math.abs(corner1[0] - corner2[0]), Math.abs(corner1[1] - corner2[1]));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }

}
