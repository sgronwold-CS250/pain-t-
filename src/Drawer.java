import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;


public abstract class Drawer extends CanvasModifier {
    protected Color color;
    protected double thickness;
    

    /**
     * @param c The canvas object
     * @param ilabel The label that will carry the instructions to the user
     */
    public Drawer(Canvas c, Labeled ilabel) {
        this(c, ilabel, null);
        
        ColorPickerDialog cpd = new ColorPickerDialog();
        color = cpd.getColor();
    }

    /**
     * @param c The canvas object
     * @param ilabel The label that will carry the instructions to the user
     * @param col (Optional) the color of the object to be drawn
     */
    public Drawer(Canvas c, Labeled ilabel, Color col) {
        super(c, ilabel);

        TextInputDialog tid = new TextInputDialog("Enter a thickness");
        String response = tid.showAndWait().get();

        thickness = Double.parseDouble(response);
        color = col;
    }

    @Override
    public void startCanvasListener(){
        super.startCanvasListener();

        canvas.getGraphicsContext2D().setStroke(color);
        canvas.getGraphicsContext2D().setLineWidth(thickness);
    }

    /**
     * Draws the thing that needs to be drawn.
     */
    protected abstract void draw();
}