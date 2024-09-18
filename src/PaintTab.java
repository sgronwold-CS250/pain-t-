import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PaintTab extends Canvas implements ChangeListener<Number> {
    Stack<Canvas> canvasStack = new Stack<Canvas>();
    
    private static GridPane grid;
    
    boolean UNSAVED_CHANGES = false;
    File currPath;

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
        canvasStack.push(c);

        add(this);

        Stage stage = (Stage) getGridPane().getScene().getWindow();

        // setting the listener for window resizing
        stage.widthProperty().addListener((ChangeListener<? super Number>) this);
        stage.heightProperty().addListener((ChangeListener<? super Number>) this);
    }

    public Canvas getCanvas() {
        return canvasStack.peek();
    }

    public static void refreshButtons() {
        // step 1 refresh the button list
        // remove all buttons and start fresh
        buttons.clear();

        for(int i = 0; i < tabs.size(); i++) {
            // make a button for each tab
            Button b;
            
            if (currTabIndex == i) b = new Button("[Tab "+i+"]");
            else b = new Button("Tab "+i);

            b.setId("tab"+i);
            b.setOnAction(new TabSwitcher());
            buttons.add(b);
        }

        // step 2 re-add the buttons
        grid = getGridPane();

        // remove the "tab" buttons
        // but we need to add the things to remove to a list
        ArrayList<Node> removeQueue = new ArrayList<Node>();
        for(Node n: grid.getChildren()) {
            if (n instanceof Button) {
                Button b = (Button) n;

                // only the buttons whose ids begin with "tab" should be included here
                if (b.getId().substring(0, 3).equalsIgnoreCase("tab")) {
                    removeQueue.add(n);
                }
            }
        }

        // now we act on our remove queue
        for(Node n: removeQueue) {
            grid.getChildren().remove(n);
        }

        // re-add the buttons
        for(int i = 0; i < buttons.size(); i++) {
            grid.add(buttons.get(i), i, BUTTON_ROW);
        }
    }

    // called by the window resize callback
    // it's also a standalone function such that we can call it whenever we want
    public void resize() {
        Scene s = getCanvas().getScene();

        double availableCanvasWidth = s.getWidth();

        // this is the height minus the y offset of the canvas; so this is the available height that we have for our canvas
        double availableCanvasHeight = s.getHeight() - getCanvas().getLayoutY();

        // if width or height is nonpositive then we don't need to worry about this
        if (availableCanvasWidth <= 0 || availableCanvasHeight <= 0) return;

        // otherwise we have a legit window to work with
        // canvas width does NOT include scaling
        double newScaleX = availableCanvasWidth/getCanvas().getWidth();
        double newScaleY = availableCanvasHeight/getCanvas().getHeight();

        double newScale = Math.min(newScaleX, newScaleY);
        
        getCanvas().setScaleX(newScale);
        getCanvas().setScaleY(newScale);

        // translate the canvas such that it appears aligned within the window
        getCanvas().setTranslateX((newScale-1)/2 * getCanvas().getWidth());
        getCanvas().setTranslateY((newScale-1)/2 * getCanvas().getHeight());
    }

    @Override
    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
        resize();
    }

    public static PaintTab getCurrentTab() {
        return tabs.get(currTabIndex);
    }

    private static void add(PaintTab pt) {
        tabs.add(pt);

        setCurrentTab(pt);
    }

    public static void setCurrentTab() {
        // just refresh the current tab
        // it'll eventually be set to the currTabIndex anyways
        setCurrentTab(currTabIndex);
    }

    public static void setCurrentTab(int i) {
        setCurrentTab(tabs.get(i));
    }

    public static void setCurrentTab(PaintTab pt) {
        int i = tabs.indexOf(pt);

        if (i == -1) {
            System.err.println("This PaintTab object doesn't exist.");
            return;
        }

        System.out.println(i);

        grid = getGridPane();

        // remove the current canvas
        // but we need to add the things to remove to a list
        ArrayList<Node> removeQueue = new ArrayList<Node>();
        for(Node n: grid.getChildren()) {
            if (n instanceof Canvas) {
                removeQueue.add(n);
            }
        }

        // now we act on our remove queue
        for(Node n: removeQueue) {
            grid.getChildren().remove(n);
        }

        // now that we've removed the canvas,
        // we can add our new canvas
        currTabIndex = i;

        // add a new canvas
        grid.add(getCurrentTab().getCanvas(), 0, CANVAS_ROW, Main.menuButtons.length, 1);

        // change the action listeners over to the new canvas
        Main.menuActionListener.setCanvas(getCurrentTab().getCanvas());
        Main.menuKeyListener.setCanvas(getCurrentTab().getCanvas());

        // now the buttons need to be refreshed
        refreshButtons();
    }

    public static void remove() {
        // this just removes the current paint tab
        remove(getCurrentTab());
    }

    public static void remove(PaintTab pt) {
        // make sure that there's actually a tab left
        if (tabs.size() == 1) {
            System.err.println("Cannot remove the only tab remaining!");
            return;
        }

        int i = tabs.indexOf(pt);

        // make sure that pt is actually one of our tabs
        if (i == -1) {
            System.err.println("Cannot remove tab that doesn't exist");
            return;
        }

        // change the current tab to the previous one, if applicable
        if (currTabIndex != 0) currTabIndex--;

        // finally, remove the tab and refresh the current tab
        tabs.remove(pt);

        setCurrentTab();
    }

    public static GridPane getGridPane() {
        // if we already have a gridpane stored then we don't need to worry about this
        if (grid != null) return grid;

        // this is probably a gridpane
        Parent parent = getCurrentTab().getCanvas().getParent();

        if (parent instanceof GridPane) {
            return (GridPane) parent;
        } else {
            System.err.println("ERROR: This canvas' parent is not a GridPane.");
            System.err.println(parent);
            return null;
        }
    }

    public File getPath() {
        return currPath;
    }

    public void setPath(File f) {
        currPath = f;
    }
}
