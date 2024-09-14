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

    Button menuButtons[] = new Button[11];

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // basic window things
        stage.setTitle("Pain (t)");
        stage.setWidth(720);
        stage.setHeight(720);

        // we setup the grid as we go
        grid = new GridPane();

        // setting the listener for window resizing
        stage.widthProperty().addListener((ChangeListener<? super Number>) this);
        stage.heightProperty().addListener((ChangeListener<? super Number>) this);

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid, 300, 400);
        stage.setScene(scene);

        canvas = new Canvas(500, 500);
        // grid.add(item, col, row, col-span, row-span);
        grid.add(canvas, 0, 3, menuButtons.length, 1);

        // title
        Label title = new Label("Buy me a Java at paypal.me/samuelgronwold");
        title.setId("title");
        title.setWrapText(true);

        // grid.add(item, col, row, col-span, row-span);
        grid.add(title, 0, 0, menuButtons.length, 1);

        refreshCanvasDims(canvas);

        // initialize buttons
        menuButtons[0] = new Button("Load image...");
        menuButtons[0].setId("open");
        menuButtons[0].setOnAction(new MenuActionListener(canvas));

        menuButtons[1] = new Button("Save As...");
        menuButtons[1].setId("saveas");
        menuButtons[1].setOnAction(menuButtons[0].getOnAction());

        menuButtons[2] = new Button("Help...");
        menuButtons[2].setId("help");
        menuButtons[2].setOnAction(menuButtons[0].getOnAction());

        menuButtons[3] = new Button("Draw line...");
        menuButtons[3].setId("drawline");
        menuButtons[3].setOnAction(menuButtons[0].getOnAction());

        menuButtons[4] = new Button("Draw triangle...");
        menuButtons[4].setId("drawtriangle");
        menuButtons[4].setOnAction(menuButtons[0].getOnAction());

        menuButtons[5] = new Button("Pencil");
        menuButtons[5].setId("pencil");
        menuButtons[5].setOnAction(menuButtons[0].getOnAction());

        menuButtons[6] = new Button("Draw square");
        menuButtons[6].setId("drawsquare");
        menuButtons[6].setOnAction(menuButtons[0].getOnAction());

        menuButtons[7] = new Button("Draw ellipse");
        menuButtons[7].setId("drawellipse");
        menuButtons[7].setOnAction(menuButtons[0].getOnAction());

        menuButtons[8] = new Button("Draw rectangle");
        menuButtons[8].setId("drawrectangle");
        menuButtons[8].setOnAction(menuButtons[0].getOnAction());

        menuButtons[9] = new Button("Draw circle");
        menuButtons[9].setId("drawcircle");
        menuButtons[9].setOnAction(menuButtons[0].getOnAction());

        menuButtons[10] = new Button("Eyedropper");
        menuButtons[10].setId("eyedropper");
        menuButtons[10].setOnAction(menuButtons[0].getOnAction());

        for(int i = 0; i < menuButtons.length; i++) {
            grid.add(menuButtons[i], i, 2);
        }

        // finally listen for the key events
        scene.setOnKeyPressed(new MenuKeyListener(canvas));

        stage.show();
    }


    // called by the window resize callback
    // it's also a standalone function such that we can call it whenever we want
    public static void refreshCanvasDims(Canvas c) {
        Scene s = c.getScene();

        double availableCanvasWidth = s.getWidth();

        // this is the height minus the y offset of the canvas; so this is the available height that we have for our canvas
        double availableCanvasHeight = s.getHeight() - c.getLayoutY();

        // if width or height is nonpositive then we don't need to worry about this
        if (availableCanvasWidth <= 0 || availableCanvasHeight <= 0) return;

        // otherwise we have a legit window to work with
        // canvas width does NOT include scaling
        double newScaleX = availableCanvasWidth/c.getWidth();
        double newScaleY = availableCanvasHeight/c.getHeight();

        double newScale = Math.min(newScaleX, newScaleY);
        
        c.setScaleX(newScale);
        c.setScaleY(newScale);

        // translate the canvas such that it appears aligned within the window
        c.setTranslateX((newScale-1)/2 * c.getWidth());
        c.setTranslateY((newScale-1)/2 * c.getHeight());
    }


    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        refreshCanvasDims(canvas);
    }
}