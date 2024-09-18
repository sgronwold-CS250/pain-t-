import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;

public class LineDrawer extends Drawer {
    double startX, startY, endX, endY;
    boolean gotStartPoint = false;

    public LineDrawer(Canvas c, Labeled ilabel) {
        super(c, ilabel);
        instructionLabel.setText("Click where you want the line to start");
        
        super.startCanvasListener();
    }

    @Override
    public void handle(MouseEvent e) {
        System.out.println("made a click to form a line");

        if(!gotStartPoint) {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                // then we need to make the starting point of the line
                startX = endX = e.getX();
                startY = endY = e.getY();
                gotStartPoint = true;
                instructionLabel.setText("Click where you want the line to end");

                predraw(true);
                draw();
            }
        } else {
            // then we need to make the ending point of the line
            endX = e.getX();
            endY = e.getY();

            
            predraw();
            draw();

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                // deregister ourselves
                super.stopCanvasListener();

                instructionLabel.setText("Line drawn!");
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
        canvas.getGraphicsContext2D().strokeLine(startX, startY, endX, endY);
    }   
}