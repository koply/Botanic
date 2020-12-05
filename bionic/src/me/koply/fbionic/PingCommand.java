package me.koply.fbionic;

import me.koply.botanic.bot.command.CommandUtils;
import me.koply.botanic.bot.command.annotation.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Command(names={"ping"}, description = "Ponk!")
public class PingCommand implements CommandUtils {

    @Override
    public void handle(@NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(embed("Ponk!")).queue();
    }
}