import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    Scene scene;
    GridPane grid;
    Canvas canvas;

    Button menuButtons[] = new Button[1];

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // basic window things
        stage.setTitle("Paint");
        stage.setWidth(1280);
        stage.setHeight(720);

        // we setup the grid as we go
        grid = new GridPane();

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid, 300, 400);
        stage.setScene(scene);

        // initialize buttons
        menuButtons[0] = new Button("Load image...");
        menuButtons[0].setOnAction(new MenuActionListener());
        
        canvas = new Canvas(500, 500);
        grid.add(canvas, 0, 2);

        for(int i = 0; i < menuButtons.length; i++) {
            grid.add(menuButtons[i], i, 1);
        }

        // title
        Label title = new Label("Let Pain (t) be a strictly increasing function. The value of Pain at any given value t exceeds expectations.");
        grid.add(title, 0, 0);

        stage.show();
    }
}