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
        String bodyText = "This is Pain (t)\n";
        bodyText += "\"Line...\" draws a line.";
        bodyText += "Choose a thickness and color through the dialogs that get summoned.";

        Label body = new Label(bodyText);
        body.setWrapText(true);
        grid.add(body, 0, 2);

        stage.show();
    }

}