import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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

    public Color getColor() {
        return dialog.showAndWait().get();
    }

    // this is how the dialog knows how to get the colour
    @Override
    public Color call(ButtonType arg0) {
        return cp.getValue();
    }
}
