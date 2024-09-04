import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class LineDrawer implements EventHandler<MouseEvent> {
    Canvas canvas;
    Line line = new Line(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

    public LineDrawer(Canvas c) {
        canvas = c;
    }

    @Override
    public void handle(MouseEvent e) {
        System.out.println("made a click to form a line");

        if(line.getStartX() == Double.MAX_VALUE) {
            // then we need to make the starting point of the line
            line.setStartX(e.getX());
            line.setStartY(e.getY());
        }

        else if(line.getEndX() == Double.MAX_VALUE) {
            // then we need to make the ending point of the line
            line.setEndX(e.getX());
            line.setEndY(e.getY());

            canvas.getGraphicsContext2D().strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            
            // deregister ourselves
            canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
        }
    }
    
}
