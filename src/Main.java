import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application implements ChangeListener<Number> {
    Scene scene;
    GridPane grid;
    Canvas canvas;

    Button menuButtons[] = new Button[4];

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // basic window things
        stage.setTitle("Pain (t)");
        stage.setWidth(640);
        stage.setHeight(480);

        // we setup the grid as we go
        grid = new GridPane();

        // setting the listener for window resizing
        stage.widthProperty().addListener((ChangeListener<? super Number>) this);
        stage.heightProperty().addListener((ChangeListener<? super Number>) this);

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid, 300, 400);
        stage.setScene(scene);

        // initialize buttons
        menuButtons[0] = new Button("Load image...");
        menuButtons[0].setId("open");
        menuButtons[0].setOnAction(new MenuActionListener());

        menuButtons[1] = new Button("Save As...");
        menuButtons[1].setId("saveas");
        menuButtons[1].setOnAction(menuButtons[0].getOnAction());

        menuButtons[2] = new Button("Help...");
        menuButtons[2].setId("help");
        menuButtons[2].setOnAction(menuButtons[0].getOnAction());

        menuButtons[3] = new Button("Draw line...");
        menuButtons[3].setId("drawline");
        menuButtons[3].setOnAction(menuButtons[0].getOnAction());
        
        canvas = new Canvas(500, 500);
        // grid.add(item, col, row, col-span, row-span);
        grid.add(canvas, 0, 2, menuButtons.length, 1);

        for(int i = 0; i < menuButtons.length; i++) {
            grid.add(menuButtons[i], i, 1);
        }

        // title
        Label title = new Label("Let Pain (t) be a strictly increasing function. The value of Pain at any given value t exceeds expectations.");
        title.setWrapText(true);

        // grid.add(item, col, row, col-span, row-span);
        grid.add(title, 0, 0, menuButtons.length, 1);

        stage.show();
    }


    // called by the window resize callback
    // it's also a standalone function such that we can call it whenever we want
    public void refreshCanvasDims() {
        double availableCanvasWidth = scene.getWidth();

        // this is the height minus the y offset of the canvas; so this is the available height that we have for our canvas
        double availableCanvasHeight = scene.getHeight() - canvas.getLayoutY();

        // if width or height is nonpositive then we don't need to worry about this
        if (availableCanvasWidth <= 0 || availableCanvasHeight <= 0) return;

        // otherwise we have a legit window to work with
        // canvas width does NOT include scaling
        double newScaleX = availableCanvasWidth/canvas.getWidth();
        double newScaleY = availableCanvasHeight/canvas.getHeight();

        double newScale = Math.min(newScaleX, newScaleY);

        System.out.println(availableCanvasHeight);

        canvas.setScaleX(newScale);
        canvas.setScaleY(newScale);

        // translate the canvas such that it appears aligned within the window
        canvas.setTranslateX((newScale-1)/2 * canvas.getWidth());
        canvas.setTranslateY((newScale-1)/2 * canvas.getHeight());
    }


    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        refreshCanvasDims();
    }
}