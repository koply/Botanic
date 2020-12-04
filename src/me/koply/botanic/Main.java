package me.koply.botanic;

import jline.console.ConsoleReader;
import me.koply.botanic.bot.BotanicBot;
import me.koply.botanic.cli.BotanicLogger;
import me.koply.botanic.cli.LoggingOutputStream;
import me.koply.botanic.console.ConsoleService;
import me.koply.botanic.data.DataManager;
import me.koply.botanic.data.records.Config;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static Logger LOGGER;
    public static Config config;

    private Main() throws IOException {
        // initializing io things
        System.setProperty( "library.jansi.version", "Botanic" );
        AnsiConsole.systemInstall();

        ConsoleReader consoleReader = new ConsoleReader();
        consoleReader.setExpandEvents(false);

        LOGGER = new BotanicLogger("Botanic", consoleReader);
        System.setErr(new PrintStream(new LoggingOutputStream(LOGGER, Level.SEVERE), true));
        System.setOut(new PrintStream(new LoggingOutputStream(LOGGER, Level.INFO), true));
        // io things end

        config = DataManager.getInstance().readConfig();

        BotanicBot botanicBot = new BotanicBot(config);
        botanicBot.start();

        new ConsoleService(consoleReader, LOGGER).run();
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
