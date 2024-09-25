import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
    static MenuActionListener menuActionListener;
    static MenuKeyListener menuKeyListener;
    static AutoSaver autoSaver;

    static Button[] menuButtons = new Button[10];

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
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

        // title
        Label title = new Label("Buy me a Java at paypal.me/samuelgronwold");
        title.setId("title");
        title.setWrapText(true);

        // grid.add(item, col, row, col-span, row-span);
        grid.add(title, 0, 0, menuButtons.length, 1);

        menuActionListener = new MenuActionListener(canvas);

        // initialize buttons
        menuButtons[0] = new Button("line");
        menuButtons[0].setId("drawline");
        menuButtons[0].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[1] = new Button("draw");
        menuButtons[1].setId("pencil");
        menuButtons[1].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[2] = new Button("regpoly");
        menuButtons[2].setId("drawregularpolygon");
        menuButtons[2].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[3] = new Button("ellipse");
        menuButtons[3].setId("drawellipse");
        menuButtons[3].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[4] = new Button("rect");
        menuButtons[4].setId("drawrectangle");
        menuButtons[4].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[5] = new Button("circ");
        menuButtons[5].setId("drawcircle");
        menuButtons[5].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[6] = new Button("dropper");
        menuButtons[6].setId("eyedropper");
        menuButtons[6].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[7] = new Button("text");
        menuButtons[7].setId("drawtext");
        menuButtons[7].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[8] = new Button("clear");
        menuButtons[8].setId("clearcanvas");
        menuButtons[8].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        menuButtons[9] = new Button("asv disp");
        menuButtons[9].setId("toggleautosavedisplay");
        menuButtons[9].setOnAction((EventHandler<ActionEvent>) menuActionListener);

        for(int i = 0; i < menuButtons.length; i++) {
            grid.add(menuButtons[i], i, 2);
        }

        // finally listen for the key events
        menuKeyListener = new MenuKeyListener(canvas);
        scene.setOnKeyPressed(menuKeyListener);


        // get the PaintTabs set up
        new PaintTab(canvas);

        PaintTab.getCurrentTab().resize();

        PaintTab.refreshButtons();

        // scheduling the autosave to happen
        autoSaver = new AutoSaver(canvas, title);
        autoSaver.start();

        // startup the webserver
        new WebServer();

        stage.show();
    }

    @Override
    public void stop() {
        for(int i = 0; i < PaintTab.tabs.size(); i++) {
            PaintTab tab = PaintTab.tabs.get(i);

            if (!tab.UNSAVED_CHANGES) continue;
            SaveDialog sd = new SaveDialog("Tab "+i+" not saved. Do you want to save it?");
            String response = sd.getResponse();

            switch(response) {
                case "save":
                menuActionListener.save();
                break;
                case "saveas":
                menuActionListener.saveAs();
                break;
            }
        }
    }
}