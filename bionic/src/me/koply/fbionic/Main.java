package me.koply.fbionic;

import me.koply.botanic.bionic.java.Bionic;
import me.koply.botanic.bot.command.CommandUtils;
import me.koply.botanic.bot.command.annotation.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Command(names = {"bionic"}, description = "Hello World!")
public class Main extends Bionic implements CommandUtils {

    public Main() {
        System.out.println("Called the first bionics constructor!");
    }

    @Override
    public void onEnable() {
        System.out.println("The first bionic is enabled!");
        setCommandPackage(Main.class.getPackage());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void handle(@NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(embed("Hello World!")).queue();
    }
}