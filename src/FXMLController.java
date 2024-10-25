import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Container for all the FXML objects
 */
public class FXMLController {
    @FXML GridPane grid;
    @FXML Button lineDrawButton, pencilButton, regPolyButton, ellipseButton, rectangleButton,
        circleButton, eyedropperButton, textButton, clearCanvasButton, autosaveTimerToggleButton,
        hFlipButton, vFlipButton, starButton;
    @FXML Canvas canvas;

    public FXMLController() {
    }
}
