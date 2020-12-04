package me.koply.botanic.bot.command;

import me.koply.botanic.bot.command.records.Parameters;
import net.dv8tion.jda.api.JDA;

public final class CommandInitializer {

    private final Parameters params;

    public CommandInitializer(JDA jda) {
        params = Parameters.getInstance();
    }

    public void initialize() {

    }

}