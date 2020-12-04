package me.koply.botanic;

import me.koply.botanic.data.DataManager;
import me.koply.botanic.data.records.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {

    public static final Logger LOGGER = Logger.getLogger("Botanic");
    public static Config config;
    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            public String format(LogRecord record) {
                return String.format("[%s %s] %s -> %s\n", this.formatter.format(new Date(record.getMillis())), record.getLevel(), record.getLoggerName(), record.getMessage());
            }
        });
        LOGGER.addHandler(consoleHandler);
    }

    private Main() {
        config = DataManager.getInstance().readConfig();
    }

    public static void main(String[] args) {
        new Main();
    }
}
