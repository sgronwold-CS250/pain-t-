import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    Scene scene;
    GridPane grid;

    public static void main(String[] args) {
        launch("");
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Paint");
        stage.setWidth(640);
        stage.setHeight(480);

        grid = new GridPane();

        scene = new Scene(grid, 640, 480);
        stage.setScene(scene);        

        stage.show();
    }
}