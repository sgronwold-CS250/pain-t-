import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class Eyedropper extends Drawer {

    public Eyedropper(Canvas c, Labeled ilabel) {
        super(c, ilabel);
        
        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        // first we want to deregister ourselves because this might take a while
        super.stopCanvasListener();

        // get colour
        int color;

        // reset canvas scale
        canvas.setScaleX(1);
        canvas.setScaleY(1);

        // begin stack overflow code
        WritableImage snap = canvas.getGraphicsContext2D().getCanvas().snapshot(null, null);
        color = snap.getPixelReader().getArgb((int) e.getX(), (int) e.getY()); //This just gets the color without assigning it.
        // end stack overflow code

        // set canvas value just to trigger the canvas resizing
        canvas.widthProperty().setValue(canvas.widthProperty().getValue());

        // make sure all bits but the least significant 24 bits are cleared
        color &= 0xFFFFFF;

        // put the rgb code in the instruction label
        instructionLabel.setText("The colour is 0x"+String.format("%06X", color));
    }

    @SuppressWarnings("unchecked")
    @Override
    public EventType<MouseEvent>[] getEventTypes() {
        return new EventType[] {MouseEvent.MOUSE_CLICKED};
    }

}
