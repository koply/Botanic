package me.koply.botanic.console;

import java.util.HashMap;

public class ConsoleService {

    private static ConsoleService instance;
    public static ConsoleService getInstance() {
        if (instance == null) instance = new ConsoleService();
        return instance;
    }

    private final HashMap<String, IConsoleCommand> consoleCommands = new HashMap<>();
    public final HashMap<String, IConsoleCommand> getConsoleCommands() { return consoleCommands; }

    private ConsoleService() {

    }


}