import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelpScreen {

    Stage stage;
    GridPane grid;
    Scene scene;

    /**
     * Shows the help screen.
     */
    public HelpScreen() {
        stage = new Stage();

        // basic window things
        stage.setTitle("Pain (t) Help");
        stage.setWidth(640);
        stage.setHeight(480);

        // we setup the grid as we go
        grid = new GridPane();

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid, 300, 400);
        stage.setScene(scene);

        // this is the title label, it's the uppermost item on the stage
        Label title = new Label("Select an option to learn more about it.");
        title.setWrapText(true);
        grid.add(title, 0, 0);

        // this is how the user decides what they need help on
        // TODO once we have multiple categories
        MenuButton dropdown = new MenuButton("Select...");
        dropdown.getItems().add(new MenuItem("General/About"));
        grid.add(dropdown, 0, 1);

        // this is the body text where most of the text is going to be
        String bodyText = "This is Pain (t) v1.0.0\n";
        bodyText += "Keyboard shortcuts:\n";
        bodyText += "Ctrl+S save\n";
        bodyText += "Ctrl+Shift+S saveas\n";
        bodyText += "Ctrl+O open\n";
        bodyText += "Ctrl+C copy part of the canvas\n";
        bodyText += "Ctrl+V paste part of the canvas\n";


        Label body = new Label(bodyText);
        body.setWrapText(true);
        grid.add(body, 0, 2);

        stage.show();
    }

}