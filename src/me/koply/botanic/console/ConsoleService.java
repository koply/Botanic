package me.koply.botanic.console;

import me.koply.botanic.Main;
import me.koply.botanic.console.commands.CCPlugins;
import org.reflections8.Reflections;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class ConsoleService {

    private static ConsoleService instance;
    public static ConsoleService getInstance() {
        if (instance == null) instance = new ConsoleService();
        return instance;
    }

    private final HashMap<String, IConsoleCommand> consoleCommands = new HashMap<>();
    public final HashMap<String, IConsoleCommand> getConsoleCommands() { return consoleCommands; }

    private final Scanner sc = new Scanner(System.in);
    private final Logger log = Main.LOGGER.getParent();

    public void run() {
        while (true) {
            System.out.print("\n> ");
            String entry = sc.nextLine();

            if (entry.equals("exit")) {
                log.info("See you next time :)");
                System.exit(1);
            }

            if (!consoleCommands.containsKey(entry)) {
                log.info("Invalid command.");
                continue;
            }

            IConsoleCommand.ConsoleParams params = new IConsoleCommand.ConsoleParams(sc, log);
            consoleCommands.get(entry).handle(params);
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