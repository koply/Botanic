package me.koply.botanic.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public interface IConsoleCommand {
    ArrayList<String> aliases = new ArrayList<>();
    default void handle(Scanner sc) {}

    default void register(IConsoleCommand clazz) {
        HashMap<String, IConsoleCommand> commandsPtr = ConsoleService.getInstance().getConsoleCommands();
        for (String s : clazz.aliases) {
            commandsPtr.put(s,clazz);
        }
    }
}