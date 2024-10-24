import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Labeled;

public class AutoSaver extends AnimationTimer {

    private Labeled timerDisplay;
    private long nextAutoSave = 0;
    private long lastAlert = 0;
    private boolean displayEnabled = false;
    private Alert alert = new Alert(AlertType.INFORMATION);

    private int AUTOSAVE_INTERVAL = 5; // seconds

    /**
     * Constructs a AutoSaver object with the default autosave interval of five seconds.
     * @param l The Labeled object, the display for the autosave timer.
     */
    public AutoSaver(Labeled l) {
        timerDisplay = l;
        alert.setHeaderText("I AUTOSAVED YOUR PHOTO TO autosave.png");
    }


    /**
     * @param l The Labeled object, the display for the autosave timer.
     * @param autosaveInterval The autosave interval, in seconds.
     */
    public AutoSaver(Labeled l, int autosaveInterval) {
        this(l);
        AUTOSAVE_INTERVAL = autosaveInterval;
    }

    @Override
    public void handle(long tick) {
        // wait one second to hide the alert
        if (tick - lastAlert >= 1000000000L && alert.isShowing()) {
            alert.close();
        }

        if (nextAutoSave - tick <= 0) {
            nextAutoSave = tick + AUTOSAVE_INTERVAL*1000000000L;

            Main.menuActionListener.saveAs(new File("../autosave.png"), false);
            alert.show();
            lastAlert = tick;
            Main.LOGGER.log("Autosaved");
        }

        if (displayEnabled) {
            double timerOutput = (nextAutoSave - tick)/1000000000.0;
            getTimerDisplay().setText(String.format("%.1f", timerOutput));
        }
    }

    public void setDisplayEnabled(boolean newDisplayEnabled) {
        displayEnabled = newDisplayEnabled;
    }

    public boolean getDisplayEnabled() {
        return displayEnabled;
    }

    public Labeled getTimerDisplay() {
        return timerDisplay;
    }
}
