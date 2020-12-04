package me.koply.botanic.cli;


import jline.console.ConsoleReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class BotanicLogger extends Logger {

    private final LogDispatcher dispatcher = new LogDispatcher( this );

    public BotanicLogger(String loggerName, ConsoleReader reader) {
        super( loggerName, null );
        setLevel( Level.ALL );

        BConsoleHandler consoleHandler = new BConsoleHandler(reader);
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            public String format(LogRecord record) {
                return String.format("[%s %s] %s -> %s\n", this.formatter.format(new Date(record.getMillis())), record.getLevel(), record.getLoggerName(), record.getMessage());
            }
        });
        addHandler( consoleHandler );

        dispatcher.start();
    }

    @Override
    public void log(LogRecord record) {
        dispatcher.queue(record);
    }

    void doLog(LogRecord record) {
        super.log(record);
    }
}