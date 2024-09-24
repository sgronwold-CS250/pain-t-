import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Labeled;

public class AutoSaver extends AnimationTimer {

    Labeled timerDisplay;
    Canvas canvas;
    int timer;
    long nextAutoSave = 0;

    final int AUTOSAVE_INTERVAL = 5; // seconds

    public AutoSaver(Canvas c, Labeled l) {
        canvas = c;
        timerDisplay = l;

        timer = AUTOSAVE_INTERVAL;
    }

    @Override
    public void handle(long tick) {
        if (nextAutoSave - tick <= 0) {
            nextAutoSave = tick + AUTOSAVE_INTERVAL*1000000000L;

            MenuListener menuListener = new MenuListener(canvas);
            menuListener.saveAs(new File("../autosave.png"), false);
        }

        double timerOutput = (nextAutoSave - tick)/1000000000.0;
        timerDisplay.setText(String.format("%.1f", timerOutput));
    }
}
