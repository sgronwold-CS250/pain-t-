import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    static boolean UNSAVED_CHANGES = false;
    MenuListener menuListener;

    static Button menuButtons[] = new Button[13];

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) {
        // basic window things
        stage.setTitle("Pain (t)");
        stage.setWidth(720);
        stage.setHeight(720);

        // we setup the grid as we go
        grid = new GridPane();

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid, 300, 400);
        stage.setScene(scene);

        canvas = new Canvas(500, 500);
        // grid.add(item, col, row, col-span, row-span);
        grid.add(canvas, 0, PaintTab.CANVAS_ROW, menuButtons.length, 1);

        PaintTab tab = new PaintTab(canvas);

        // setting the listener for window resizing
        stage.widthProperty().addListener((ChangeListener<? super Number>) tab);
        stage.heightProperty().addListener((ChangeListener<? super Number>) tab);

        // title
        Label title = new Label("Buy me a Java at paypal.me/samuelgronwold");
        title.setId("title");
        title.setWrapText(true);

        // grid.add(item, col, row, col-span, row-span);
        grid.add(title, 0, 0, menuButtons.length, 1);

        menuListener = new MenuActionListener(canvas);

        // initialize buttons
        menuButtons[0] = new Button("Load image...");
        menuButtons[0].setId("open");
        menuButtons[0].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[1] = new Button("Save As...");
        menuButtons[1].setId("saveas");
        menuButtons[1].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[2] = new Button("Help...");
        menuButtons[2].setId("help");
        menuButtons[2].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[3] = new Button("Draw line...");
        menuButtons[3].setId("drawline");
        menuButtons[3].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[4] = new Button("Draw triangle...");
        menuButtons[4].setId("drawtriangle");
        menuButtons[4].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[5] = new Button("Pencil");
        menuButtons[5].setId("pencil");
        menuButtons[5].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[6] = new Button("Draw square");
        menuButtons[6].setId("drawsquare");
        menuButtons[6].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[7] = new Button("Draw ellipse");
        menuButtons[7].setId("drawellipse");
        menuButtons[7].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[8] = new Button("Draw rectangle");
        menuButtons[8].setId("drawrectangle");
        menuButtons[8].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[9] = new Button("Draw circle");
        menuButtons[9].setId("drawcircle");
        menuButtons[9].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[10] = new Button("Eyedropper");
        menuButtons[10].setId("eyedropper");
        menuButtons[10].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[11] = new Button("New Tab");
        menuButtons[11].setId("newtab");
        menuButtons[11].setOnAction((EventHandler<ActionEvent>) menuListener);

        menuButtons[12] = new Button("Close this tab");
        menuButtons[12].setId("closetab");
        menuButtons[12].setOnAction((EventHandler<ActionEvent>) menuListener);

        for(int i = 0; i < menuButtons.length; i++) {
            grid.add(menuButtons[i], i, 2);
        }

        // finally listen for the key events
        scene.setOnKeyPressed(new MenuKeyListener(canvas));


        // get the PaintTabs set up
        PaintTab.add(tab);

        PaintTab.getCurrentTab().resize();

        PaintTab.refreshButtons();

        stage.show();
    }

    @Override
    public void stop() {
        if (!UNSAVED_CHANGES) return;
        SaveDialog sd = new SaveDialog();
        String response = sd.getResponse();

        switch(response) {
            case "save":
            menuListener.save();
            break;
            case "saveas":
            menuListener.saveAs();
            break;
        }
    }
}