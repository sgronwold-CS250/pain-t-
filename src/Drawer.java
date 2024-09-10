import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public abstract class Drawer implements EventHandler<MouseEvent> {
    Canvas canvas;
    Color color;
    double thickness;
    Label instructionLabel;

    public Drawer(Canvas c, Label ilabel) {
        canvas = c;
        instructionLabel = ilabel;

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        thickness = Double.parseDouble(response);

        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
    }

}