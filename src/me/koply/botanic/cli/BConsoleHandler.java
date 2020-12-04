package me.koply.botanic.cli;

import jline.console.ConsoleReader;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BConsoleHandler extends Handler {

    private final ConsoleReader console;
    public BConsoleHandler(ConsoleReader console)
    {
        this.console = console;
    }

    public void print(String s)
    {
        try {
            console.print( Ansi.ansi().eraseLine( Ansi.Erase.ALL ).toString() + ConsoleReader.RESET_LINE + s + Ansi.ansi().reset().toString() );
            console.drawLine();
            console.flush();
        } catch ( IOException ex ) {}
    }

    @Override
    public void publish(LogRecord record)
    {
        if ( isLoggable( record ) )
        {
            print( getFormatter().format( record ) );
        }
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }
}