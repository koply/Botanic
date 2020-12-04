package me.koply.botanic.bot.command.commands;

import me.koply.botanic.bot.command.CommandUtils;
import me.koply.botanic.bot.command.annotation.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Command(names = {"ping"}, description = "Ponk!")
public class CPing implements CommandUtils {

    @Override
    public void handle(@NotNull MessageReceivedEvent e) {
        e.getTextChannel().sendMessage(embed("Ponk!")).queue();
    }
}