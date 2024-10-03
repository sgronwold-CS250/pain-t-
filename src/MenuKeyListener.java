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
        // ctrl+s for saving, ctrl+shift+s for saveas
        if (e.getCode() == KeyCode.S && e.isControlDown() && e.isShiftDown()) super.saveAs();
        else if (e.getCode() == KeyCode.S && e.isControlDown()) super.save();

        // help screen
        if (e.getCode() == KeyCode.F1) new HelpScreen();

        // ctrl+o for opening
        if (e.getCode() == KeyCode.O && e.isControlDown()) super.open();

        // ctrl+z, ctrl+y
        if (e.getCode() == KeyCode.Z && e.isControlDown()) super.undo();
        if (e.getCode() == KeyCode.Y && e.isControlDown()) super.redo();

        // copy, cut, paste
        if (e.getCode() == KeyCode.C && e.isControlDown()) super.copy();
        if (e.getCode() == KeyCode.X && e.isControlDown()) super.cut();
        if (e.getCode() == KeyCode.V && e.isControlDown()) super.paste();

        // tabbing ctrl+t and ctrl+w
        if (e.getCode() == KeyCode.T && e.isControlDown()) new PaintTab();
        if (e.getCode() == KeyCode.W && e.isControlDown()) PaintTab.remove(PaintTab.getCurrentTab());

        // ctrl+k to rotate 90 degs clockwise
        if (e.getCode() == KeyCode.K && e.isControlDown()) super.rotate();
    }
    
}
