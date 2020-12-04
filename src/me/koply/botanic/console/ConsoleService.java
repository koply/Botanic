package me.koply.botanic.console;

import jline.console.ConsoleReader;
import me.koply.botanic.console.commands.CCPlugins;
import org.reflections8.Reflections;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class ConsoleService {

    private final ConsoleReader rd;
    public final ConsoleReader getReader() { return rd; }
    private final Logger logger;
    public final Logger getLogger() { return logger; }

    public ConsoleService(ConsoleReader reader, Logger logger) {
        rd = reader;
        this.logger = logger;
    }

    private static final HashMap<String, IConsoleCommand> consoleCommands = new HashMap<>();
    public static HashMap<String, IConsoleCommand> getConsoleCommands() { return consoleCommands; }

    public void run() {
        try {
            while (true) {
                System.out.print("\n> ");
                String entry = rd.readLine();

                if (entry.equals("exit")) {
                    logger.info("See you next time :)");
                    System.exit(1);
                }

                if (!consoleCommands.containsKey(entry)) {
                    logger.info("Invalid command.");
                    continue;
                }

                IConsoleCommand.ConsoleParams params = new IConsoleCommand.ConsoleParams(this);
                consoleCommands.get(entry).handle(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerCommands() {
        Reflections ref = new Reflections(CCPlugins.class.getPackage().getName());
        Set<Class<? extends IConsoleCommand>> classes = ref.getSubTypesOf(IConsoleCommand.class);
        for (Class<? extends IConsoleCommand> clazz : classes) {
            try {
                IConsoleCommand cmd = clazz.newInstance();
                cmd.getAliases().forEach((s) -> consoleCommands.put(s, cmd));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}