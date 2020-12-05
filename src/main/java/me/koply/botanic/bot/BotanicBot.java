package me.koply.botanic.bot;

import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bot.command.CommandHandler;
import me.koply.botanic.bot.command.CommandInitializer;
import me.koply.botanic.bot.command.records.Parameters;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.ArrayList;

public class BotanicBot {

    private final String token;
    public BotanicBot(String token, ArrayList<BionicFile> bionicFiles) {
        this.token = token;
        new CommandInitializer(bionicFiles).initialize();
    }

    public void start() {
        try {
            JDA jda = JDABuilder.createDefault(token)
                    .setAutoReconnect(true)
                    .build();
            jda.awaitReady();

            jda.addEventListener(new CommandHandler(Parameters.getInstance()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}