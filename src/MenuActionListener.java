import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

/**
 * Listens to the button presses.
 */
public class MenuActionListener extends MenuListener implements EventHandler<ActionEvent> {
    public MenuActionListener(Canvas c) {
        super(c);
    }

    @Override
    public void handle(ActionEvent e) {
        System.out.println("Button clicked");
        Button button;
        if (e.getSource() instanceof Button) {
            button = (Button) e.getSource();
        } else {
            // otherwise there's nothing for us to do
            return;
        }

        switch(button.getId()) {
            case "open":
            super.open();
            return;
            case "saveas":
            super.saveAs();
            return;
            case "help":
            new HelpScreen();
            return;
        }

        super.clearAllDrawers();

        switch(button.getId()) {
            // now we're on to the drawers
            case "drawline":
            currDrawer = new LineDrawer(canvas, instructionLabel);
            return;
            case "drawtriangle":
            currDrawer = new PolygonDrawer(canvas, instructionLabel, 3);
            return;
            case "pencil":
            currDrawer = new Pencil(canvas, instructionLabel);
            return;
            case "drawregularpolygon":
            currDrawer = new RegularPolygonDrawer(canvas, instructionLabel);
            return;
            case "drawellipse":
            currDrawer = new EllipseDrawer(canvas, instructionLabel);
            return;
            case "drawrectangle":
            currDrawer = new RectangleDrawer(canvas, instructionLabel);
            return;
            case "drawcircle":
            currDrawer = new CircleDrawer(canvas, instructionLabel);
            return;
            case "eyedropper":
            currDrawer = new Eyedropper(canvas, instructionLabel);
            return;
            case "newtab":
            new PaintTab();
            return;
            case "closetab":
            PaintTab.remove(PaintTab.getCurrentTab());
            return;
            case "drawpentagon":
            currDrawer = new PolygonDrawer(canvas, instructionLabel, 5);
            return;
            case "drawhexagon":
            currDrawer = new PolygonDrawer(canvas, instructionLabel, 6);
            return;
            case "drawtext":
            currDrawer = new TextDrawer(canvas, instructionLabel);
            return;
            case "clearcanvas":
            super.clearCanvas();
            return;
            case "toggleautosavedisplay":
            Main.autoSaver.setDisplayEnabled(!Main.autoSaver.getDisplayEnabled());
            return;
            case "rotateimage":
            currDrawer = new ImageRotater(canvas, instructionLabel);
            return;
            case "hflip":
            super.hflip();
            return;
            case "vflip":
            super.vflip();
            return;
            case "drawstar":
            currDrawer = new StarDrawer(canvas, instructionLabel);
        }
    }
}