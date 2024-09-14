import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

public class MenuActionListener extends MenuListener implements EventHandler<ActionEvent> {
    public MenuActionListener(Canvas c) {
        super(c);
    }

    @Override
    public void handle(ActionEvent e) {
        super.clearAllDrawers();

        System.out.println("Button clicked");
        Button button;
        if (e.getSource() instanceof Button) {
            button = (Button) e.getSource();
        } else {
            // otherwise there's nothing for us to do
            return;
        }

        Scene scene = canvas.getScene();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        switch(button.getId()) {
            case "open":
            super.open();
            break;
            case "saveas":
            super.saveAs();
            break;
            case "help":
            new HelpScreen();
            break;
            case "drawline":
            currDrawer = new LineDrawer(canvas, instructionLabel);
            break;
            case "drawtriangle":
            currDrawer = new PolygonDrawer(canvas, instructionLabel, 3);
            break;
            case "pencil":
            currDrawer = new Pencil(canvas, instructionLabel);
            break;
            case "drawsquare":
            currDrawer = new SquareDrawer(canvas, instructionLabel);
            break;
            case "drawellipse":
            currDrawer = new EllipseDrawer(canvas, instructionLabel);
            break;
            case "drawrectangle":
            currDrawer = new RectangleDrawer(canvas, instructionLabel);
            break;
            case "drawcircle":
            currDrawer = new CircleDrawer(canvas, instructionLabel);
            break;
            case "eyedropper":
            currDrawer = new Eyedropper(canvas, instructionLabel);
            break;
        }
    }
}