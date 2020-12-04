package me.koply.botanic.console.commands;

import me.koply.botanic.console.IConsoleCommand;

import java.util.Scanner;

public class CCPlugins implements IConsoleCommand {

    public CCPlugins() {
        aliases.add("plugins");
        register(this);
    }

    @Override
    public void handle(Scanner sc) {

    }
}