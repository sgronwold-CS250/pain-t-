import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuKeyListener extends MenuListener implements EventHandler<KeyEvent> {

    public MenuKeyListener(Canvas c) {
        super(c);
    }

    @Override
    public void handle(KeyEvent e) {
        if (e.getCode() == KeyCode.S && e.isControlDown()) super.save();

        if (e.getCode() == KeyCode.Z && e.isControlDown()) super.undo();
        if (e.getCode() == KeyCode.Y && e.isControlDown()) super.redo();
    }
    
}
