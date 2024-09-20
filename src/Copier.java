import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Copier extends Drawer {
    boolean cutEnabled = false;

    public Copier(Canvas c, Labeled ilabel) {
        super(c, ilabel);

        ilabel.setText("Click upper left corner of where to copy");
        startCanvasListener();
    }

    public Copier(Canvas c, Labeled ilabel, boolean cutMode) {
        this(c, ilabel);

        cutEnabled = cutMode;
    }

    double[] corner1 = new double[2];
    double[] corner2 = new double[2];

    boolean gotCorner1 = false;

    @Override
    public void handle(MouseEvent e) {
        if (!gotCorner1) {
            corner1[0] = e.getX();
            corner1[1] = e.getY();

            instructionLabel.setText("Click lower right corner of where to copy");
            gotCorner1 = true;
        } else {
            corner2[0] = e.getX();
            corner2[1] = e.getY();

            // now we're snapshotting and storing in the clipboard
            double[] upperLeftCorner = new double[] {
                Math.min(corner1[0], corner2[0]),
                Math.min(corner1[1], corner2[1])
            };

            double[] lowerRightCorner = new double[] {
                Math.max(corner1[0], corner2[0]),
                Math.max(corner1[1], corner2[1])
            };

            // save only the selected viewport to the clipboard
            // but to snapshot, we need to set the scale to 1 for the canvas to export properly
            double oldScaleX = canvas.getScaleX();
            double oldScaleY = canvas.getScaleY();

            canvas.setScaleX(1);
            canvas.setScaleY(1);

            int viewPortWidth = (int) (lowerRightCorner[0] - upperLeftCorner[0]);
            int viewPortHeight = (int) (lowerRightCorner[1] - upperLeftCorner[1]);

            // dy and dx for the viewport
            int dy = (int) (canvas.getScene().getHeight() - canvas.getHeight() - canvas.getTranslateY()); //(canvas.getTranslateY() - canvas.getScene().getHeight() + canvas.getHeight());
            int dx = (int) canvas.getTranslateX();

            SnapshotParameters sp = new SnapshotParameters();
            sp.setViewport(new Rectangle2D(upperLeftCorner[0] + dx, upperLeftCorner[1] + dy, viewPortWidth, viewPortHeight));
            clipboard = new WritableImage(viewPortWidth, viewPortHeight);
            canvas.snapshot(sp, clipboard);

            canvas.setScaleX(oldScaleX);
            canvas.setScaleY(oldScaleY);

            instructionLabel.setText("Done copying");
            stopCanvasListener();

            // if we're cutting then fill with a white rectangle
            if (cutEnabled) {
                canvas.getGraphicsContext2D().setFill(Color.WHITE);
                canvas.getGraphicsContext2D().fillRect(upperLeftCorner[0], upperLeftCorner[1], viewPortWidth, viewPortHeight);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
    
}
