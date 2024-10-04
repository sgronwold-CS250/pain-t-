import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class FXMLController {
    @FXML GridPane grid;
    @FXML Button lineDrawButton, pencilButton, regPolyButton, ellipseButton, rectangleButton,
        circleButton, eyedropperButton, textButton, clearCanvasButton, autosaveTimerToggleButton, imageRotateButton;
    @FXML Canvas canvas;

    public FXMLController() {
        System.out.println("here");
    }
}
