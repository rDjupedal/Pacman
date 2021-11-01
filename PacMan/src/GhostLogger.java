import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GhostLogger {

    protected static Logger logger = null;

    protected GhostLogger() {

    }

    public static synchronized Logger getLogger() {
        if (logger == null) {
            initializeLogger();
        }
        return logger;

    }

    private static void initializeLogger() {

        logger = Logger.getLogger(GhostLogger.class.getName());
        logger.setUseParentHandlers(false);

        SimpleFormatter formatter = new SimpleFormatter() {

            @Override
            public synchronized String format(LogRecord logRecord) {

                return String.format("%s%n", logRecord.getMessage());
            }
        };

        try {
            String LOG_FILE_PATH = System.getProperty("user.dir") + "/PacMan/src/resources/ghost.log";
            FileHandler fh = new FileHandler(LOG_FILE_PATH);
            fh.setFormatter(formatter);
            fh.setLevel(Level.INFO);
            logger.addHandler(fh);

        } catch (Exception e) {
            System.out.println("Unable to set file logger: " + e.getMessage());
        }
    }

}
