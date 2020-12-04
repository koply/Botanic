package me.koply.botanic.console.commands;

import me.koply.botanic.console.ConsoleService;
import me.koply.botanic.console.IConsoleCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CCHelp implements IConsoleCommand {

    private final HashMap<String, IConsoleCommand> consoleCommands;
    public CCHelp() {
        consoleCommands =ConsoleService.getInstance().getConsoleCommands();
        aliases.add("help");
        register(this);
    }

    @Override
    public void handle(ConsoleParams p) {
        final HashSet<IConsoleCommand> set = new HashSet<>();
        for (Map.Entry<String, IConsoleCommand> entry : consoleCommands.entrySet()) {
            if (set.contains(entry.getValue())) continue;
            p.getLogger().info(entry.getKey() + " -> " + entry.getValue().getDescription());
            set.add(entry.getValue());
        }
    }
}