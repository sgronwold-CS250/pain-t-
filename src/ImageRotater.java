import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Labeled;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class ImageRotater extends Drawer {

    private double[] corner1 = new double[2];
    private double[] corner2 = new double[2];

    private double[] mouseCoords = new double[2];

    private boolean gotUpperLeftCorner = false;
    private boolean gotLowerRightCorner = false;

    public ImageRotater(Canvas c, Labeled ilabel) {
        super(c, ilabel, null);

        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        // we always want to update these
        mouseCoords[0] = e.getX();
        mouseCoords[1] = e.getY();

        if (!gotUpperLeftCorner) {
            // then we only want to fire if there was a mouse click
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                corner1[0] = e.getX();
                corner1[1] = e.getY();

                gotUpperLeftCorner = true;
            }
        }
        else if (!gotLowerRightCorner) {
            // still only want to fire if there is a mouse click
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                corner2[0] = e.getX();
                corner2[1] = e.getY();

                gotLowerRightCorner = true;

                predraw(true);
                draw();
            }
        }
        else {
            // now we just want to live draw
            predraw();
            draw();

            // if we clicked the mouse then stop calling this method
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                super.stopCanvasListener();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED, MouseEvent.MOUSE_MOVED};
    }

    @Override
    protected void draw() {
        // some more parameters for ease of doing everything else
        double[] upperLeftCorner = new double[] {
            Math.min(corner1[0], corner2[0]),
            Math.min(corner1[1], corner2[1])
        };

        double[] lowerRightCorner = new double[] {
            Math.max(corner1[0], corner2[0]),
            Math.max(corner1[1], corner2[1])
        };

        double viewPortWidth = lowerRightCorner[0] - upperLeftCorner[0];
        double viewPortHeight = lowerRightCorner[1] - upperLeftCorner[1];

        // this is the vector for new x-axis
        double[] newXAxis = new double[] {
            mouseCoords[0] - (corner1[0] + corner2[0])/2,
            mouseCoords[1] - (corner1[1] + corner2[1])/2
        };
        // and the associated angle
        double angle = Math.atan(newXAxis[1]/newXAxis[0])*180/Math.PI;
        // what if we get the wrong atan
        if (newXAxis[0] < 0) angle += 180;

        canvas.setScaleX(1);
        canvas.setScaleY(1);

        // dy for the viewport
        // height that isn't due to the canvas
        // i.e. other ui stuff
        int dy = (int) (canvas.getScene().getHeight() - canvas.getHeight());

        clipboard = new WritableImage((int) viewPortWidth, (int) viewPortHeight);
        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new Rectangle2D(
            upperLeftCorner[0],
            upperLeftCorner[1] + dy,
            viewPortWidth,
            viewPortHeight
        ));
        canvas.snapshot(params, clipboard);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.save();
        gc.translate(
            upperLeftCorner[0] + viewPortWidth/2,
            upperLeftCorner[1] + viewPortHeight/2
        );
        gc.rotate(angle);
        gc.drawImage(
            clipboard,
            0,
            0
        );
        gc.restore();

        PaintTab.getCurrentTab().resize();

        System.out.println("drew image");
    }
}
