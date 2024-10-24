import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * JavaFX Dialog box that lets the user pick a color.
 */
public class ColorPickerDialog implements Callback<ButtonType, Color> {
    Dialog<Color> dialog;
    ColorPicker cp;

    public ColorPickerDialog() {
        // setting up a dialog box with:
        // color picker
        // button to close and finalise ur selection
        dialog = new Dialog<Color>();
        GridPane grid = new GridPane();

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(this);

        cp = new ColorPicker();
        grid.add(cp, 0, 0);

        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Enter", ButtonData.OK_DONE));
    }

    /**
     * Shows the dialog box, waits for the user to select a color.
     * @return The color object, as a JavaFX Color object.
     */
    public Color getColor() {
        return dialog.showAndWait().get();
    }

    /**
     * this is how the dialog knows how to get the colour
     * @return The Color value of the on-board ColorPicker object.
     */
    @Override
    public Color call(ButtonType arg0) {
        return cp.getValue();
    }
}
