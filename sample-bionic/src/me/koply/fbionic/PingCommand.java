package me.koply.fbionic;


import me.koply.botanic.bot.kcommando.Command;
import me.koply.botanic.bot.kcommando.internal.Commander;
import me.koply.botanic.util.Util;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Commander(name = "Ping",
        aliases = "ping",
        description = "Pong!")
public class PingCommand extends Command {

    @Override
    public boolean handle(@NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(Util.embed("Pong!")).queue();
        return true;
    }
}