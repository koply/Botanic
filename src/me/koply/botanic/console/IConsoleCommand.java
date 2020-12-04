package me.koply.botanic.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public interface IConsoleCommand {
    ArrayList<String> aliases = new ArrayList<>();
    String description = "";
    default void handle(ConsoleParams p) {}

    default void register(IConsoleCommand clazz) {
        HashMap<String, IConsoleCommand> commandsPtr = ConsoleService.getInstance().getConsoleCommands();
        for (String s : clazz.aliases) {
            commandsPtr.put(s,clazz);
        }
    }

    default ArrayList<String> getAliases() {
        return aliases;
    }
    default String getDescription() { return description; }

    final class ConsoleParams {
        public ConsoleParams(Scanner sc, Logger logger) {
            this.sc = sc;
            this.logger = logger;
        }
        private final Scanner sc;
        private final Logger logger;

        public final Scanner getSc() {
            return sc;
        }

        public final Logger getLogger() {
            return logger;
        }
    }


}

