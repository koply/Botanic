package me.koply.botanic.console;


import jline.console.ConsoleReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public interface IConsoleCommand {
    ArrayList<String> aliases = new ArrayList<>();
    String description = "";
    default void handle(ConsoleParams p) {}

    default void register(IConsoleCommand clazz) {
        HashMap<String, IConsoleCommand> commandsPtr = ConsoleService.getConsoleCommands();
        for (String s : clazz.aliases) {
            commandsPtr.put(s,clazz);
        }
    }

    default ArrayList<String> getAliases() {
        return aliases;
    }
    default String getDescription() { return description; }

    final class ConsoleParams {
        public ConsoleParams(ConsoleService service) {
            this.rd = service.getReader();
            this.logger = service.getLogger();
        }
        private final ConsoleReader rd;
        public final ConsoleReader getReader() { return rd;}

        private final Logger logger;
        public final Logger getLogger() {
            return logger;
        }
    }


}

