import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LineDrawer extends Drawer {
    double startX, startY, endX, endY;

    boolean gotStartPoint = false;

    public LineDrawer(Canvas c, Label ilabel) {
        super(c, ilabel);
    }

    @Override
    public void handle(MouseEvent e) {
        System.out.println("made a click to form a line");

        if(!gotStartPoint) {
            // then we need to make the starting point of the line
            startX = e.getX();
            startY = e.getY();
            gotStartPoint = true;
        } else {
            // then we need to make the ending point of the line
            endX = e.getX();
            endY = e.getY();

            canvas.getGraphicsContext2D().setLineWidth(thickness);
            canvas.getGraphicsContext2D().setStroke(color);
            canvas.getGraphicsContext2D().strokeLine(startX, startY, endX, endY);

            // deregister ourselves
            canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
        }
    }   
}
