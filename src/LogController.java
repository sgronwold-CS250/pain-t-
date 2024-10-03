import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Calendar;
import java.util.TimeZone;

import java.text.SimpleDateFormat;

public class LogController {
    private final String URL = String.format("../log.log");
    private final File LOGFILE = new File(URL);

    private final SimpleDateFormat DATETIMEFORMAT = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss.SSS");

    BufferedWriter writer;

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


        log("Program started.");
    }


    public void log(String msg) {
        
        try {
            writer = new BufferedWriter(new FileWriter(URL, true));

            writer.append(DATETIMEFORMAT.format(Calendar.getInstance()));


            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
