import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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
    static final LogController LOGGER = new LogController();

    static Button[] menuButtons;

    public static void main(String[] args) {
        System.out.println("Launching Pain(t)");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // grab the fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./thefxml.fxml"));
        loader.load();

        // and get the controller
        FXMLController fxmlController = loader.getController();

        // and get the stuff from the controller
        grid = fxmlController.grid;

        menuButtons = new Button[] {
            fxmlController.lineDrawButton,
            fxmlController.pencilButton,
            fxmlController.regPolyButton,
            fxmlController.ellipseButton,
            fxmlController.rectangleButton,
            fxmlController.circleButton,
            fxmlController.eyedropperButton,
            fxmlController.textButton,
            fxmlController.clearCanvasButton,
            fxmlController.autosaveTimerToggleButton,
            fxmlController.imageRotateButton,
            fxmlController.hFlipButton,
            fxmlController.vFlipButton,
            fxmlController.starButton
        };

        // basic window things
        stage.setTitle("Pain (t)");
        stage.setWidth(1280);
        stage.setHeight(720);

        // this scene is the default scene, will include the menu and canvas among other things
        scene = new Scene(grid);
        stage.setScene(scene);

        canvas = fxmlController.canvas;

        // title
        Label title = new Label("Buy me a Java at paypal.me/samuelgronwold");
        title.setId("title");
        title.setWrapText(true);

        // grid.add(item, col, row, col-span, row-span);
        grid.add(title, 0, 0, menuButtons.length, 1);

        menuActionListener = new MenuActionListener(canvas);

        // initialize buttons
        for(Button b: menuButtons) {
            b.setOnAction((EventHandler<ActionEvent>) menuActionListener);
        }

        // finally listen for the key events
        menuKeyListener = new MenuKeyListener(canvas);
        scene.setOnKeyPressed(menuKeyListener);


        // get the PaintTabs set up
        new PaintTab(canvas);

        PaintTab.getCurrentTab().resize();

        PaintTab.refreshButtons();

        // scheduling the autosave to happen
        autoSaver = new AutoSaver(title);
        autoSaver.start();

        // startup the webserver
        new WebServer();

        LOGGER.log("Web server started.");

        stage.show();

        LOGGER.log("UI shown.");
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