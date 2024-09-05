import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineDrawer implements EventHandler<MouseEvent> {
    Canvas canvas;
    Line line = new Line(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    Color color;

    public LineDrawer(Canvas c) {
        canvas = c;

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        double thickness = Double.parseDouble(response);
        line.setStrokeWidth(thickness);

        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
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

            canvas.getGraphicsContext2D().setLineWidth(line.getStrokeWidth());
            canvas.getGraphicsContext2D().setStroke(color);
            canvas.getGraphicsContext2D().strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            
            // deregister ourselves
            canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
        }
    }
    
}
