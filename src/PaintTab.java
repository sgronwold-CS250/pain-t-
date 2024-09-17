import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PaintTab extends Canvas implements ChangeListener<Number> {
    Canvas canvas;

    static int currTabIndex = 0;
    static ArrayList<PaintTab> tabs = new ArrayList<PaintTab>();
    static ArrayList<Button> buttons = new ArrayList<Button>();

    static final int BUTTON_ROW = 3;
    static final int CANVAS_ROW = 4;

    public PaintTab() {
        // should have nothing but call to other constructor
        this(500, 500);
    }

    public PaintTab(int width, int height) {
        // should have nothing but call to other constructor
        this(new Canvas(width, height));
    }

    public PaintTab(Canvas c) {
        canvas = c;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public static void refreshButtons() {
        // step 1 refresh the button list
        // remove all buttons and start fresh
        buttons.clear();

        for(int i = 0; i < tabs.size(); i++) {
            // make a button for each tab
            Button b = new Button("Tab "+i);
            b.setId("tab"+i);
            buttons.add(b);
        }

        // step 2 re-add the buttons
        // this is probably a gridpane
        Parent parent = getCurrentTab().getCanvas().getParent();

        GridPane grid;
        if (parent instanceof GridPane) {
            grid = (GridPane) parent;
        } else {
            System.err.println("ERROR: This canvas' parent is not a GridPane.");
            return;
        }

        // remove the "tab" buttons
        for(Object n: Arrays.asList(grid.getChildren())) {
            if (n instanceof Button) {
                grid.getChildren().remove(n);
            }
        }

        // re-add the buttons
        for(int i = 0; i < buttons.size(); i++) {
            grid.add(buttons.get(i), i, BUTTON_ROW);
        }
    }

    // called by the window resize callback
    // it's also a standalone function such that we can call it whenever we want
    public void resize() {
        Scene s = canvas.getScene();

        double availableCanvasWidth = s.getWidth();

        // this is the height minus the y offset of the canvas; so this is the available height that we have for our canvas
        double availableCanvasHeight = s.getHeight() - canvas.getLayoutY();

        // if width or height is nonpositive then we don't need to worry about this
        if (availableCanvasWidth <= 0 || availableCanvasHeight <= 0) return;

        // otherwise we have a legit window to work with
        // canvas width does NOT include scaling
        double newScaleX = availableCanvasWidth/canvas.getWidth();
        double newScaleY = availableCanvasHeight/canvas.getHeight();

        double newScale = Math.min(newScaleX, newScaleY);
        
        canvas.setScaleX(newScale);
        canvas.setScaleY(newScale);

        // translate the canvas such that it appears aligned within the window
        canvas.setTranslateX((newScale-1)/2 * canvas.getWidth());
        canvas.setTranslateY((newScale-1)/2 * canvas.getHeight());
    }

    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        resize();
    }

    public static PaintTab getCurrentTab() {
        return tabs.get(currTabIndex);
    }

    public static void add(PaintTab pt) {
        tabs.add(pt);
    }
}
