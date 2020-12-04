package me.koply.botanic.console.commands;

import me.koply.botanic.console.IConsoleCommand;

public class CCPlugins implements IConsoleCommand {

    public CCPlugins() {
        aliases.add("plugins");
        register(this);
    }

    @Override
    public void handle(ConsoleParams p) {

    }
}