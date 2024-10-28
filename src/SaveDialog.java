import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * Dialog to warn that you're about to quit without saving
 */
public class SaveDialog implements Callback<ButtonType, String>, EventHandler<ActionEvent> {

    Dialog<String> dialog;
    String response;

    public SaveDialog(String msg) {
        // setting up a dialog box with:
        // color picker
        // button to close and finalise ur selection
        dialog = new Dialog<String>();

        GridPane grid = new GridPane();

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(this);

        Button[] buttons = new Button[3];

        buttons[0] = new Button("Save");
        buttons[0].setId("save");

        buttons[1] = new Button("Save as");
        buttons[1].setId("saveas");

        buttons[2] = new Button("Do not save");
        buttons[2].setId("donothing");

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnAction(this);
            grid.add(buttons[i], i, 1);
        }

        grid.add(new Label(msg), 0, 0, buttons.length, 1);

        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Enter", ButtonData.OK_DONE));
    }

    public SaveDialog() {
        this("");
    }

    public String getResponse() {
        return dialog.showAndWait().get();
    }

    // this is how the dialog knows how to get the colour
    @Override
    public String call(ButtonType arg0) {
        return String.valueOf(response);
    }

    @Override
    public void handle(ActionEvent e) {
        if (!(e.getSource() instanceof Button)) return;
        
        Button b = (Button) e.getSource();
        response = b.getId();
    }
}
