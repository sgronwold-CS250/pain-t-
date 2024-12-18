import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;

import java.text.SimpleDateFormat;

/**
 * Writes the logs (stored in the file log.log)
 */
public class LogController {
    private final String URL = String.format("../log.log");
    private final File LOGFILE = new File(URL);

    private final SimpleDateFormat DATETIMEFORMAT = new SimpleDateFormat("[YYYY/MM/dd HH:mm:ss.SSS] ");

    private BufferedWriter writer;

    /**
     * Starts the log. Call the log method to write a message.
     */
    public LogController() {
        System.out.println(URL);
        LOGFILE.setWritable(true);

        try {
            LOGFILE.delete();
            LOGFILE.createNewFile();
            writer = new BufferedWriter(new FileWriter(URL, true));
            writer.append("--- Pain (t), The Log ---\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        log("Log started.");
    }

    /**
     * Writes a log message, formatted with the current tab name, date, time.
     * @param msg The message to be added to the log.
     */
    public void log(String msg) {
        
        try {
            writer = new BufferedWriter(new FileWriter(URL, true));

            // datetime
            writer.append(DATETIMEFORMAT.format(Date.from(Calendar.getInstance().toInstant())));

            // current tab's index and filename
            writer.append(String.format("tab%02d ", PaintTab.currTabIndex));
            if (!PaintTab.tabs.isEmpty() && PaintTab.getCurrentTab().currPath != null) {
                writer.append(String.format("%s ", PaintTab.getCurrentTab().currPath.toString()));
            } else {
                writer.append("[UNSAVED IMAGE] ");
            }

            // custom message
            writer.append(msg);

            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
