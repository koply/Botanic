package me.koply.fbionic;

import me.koply.botanic.util.Util;
import me.koply.kcommando.integration.impl.jda.JDACommand;
import me.koply.kcommando.internal.Commando;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Commando(name = "Ping",
        aliases = "ping",
        description = "Pong!")
public class PingCommand extends JDACommand {

    @Override
    public boolean handle(@NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(Util.embed("Pong!")).queue();
        return true;
    }
}