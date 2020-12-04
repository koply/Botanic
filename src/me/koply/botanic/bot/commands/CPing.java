package me.koply.botanic.bot.commands;

import me.koply.kcommando.CommandUtils;
import me.koply.kcommando.annotations.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Command(names = {"ping"}, description = "Ponk!")
public class CPing implements CommandUtils {

    @Override
    public void handle(@NotNull MessageReceivedEvent e) {
        e.getTextChannel().sendMessage(embed("Ponk!")).queue();
    }
}