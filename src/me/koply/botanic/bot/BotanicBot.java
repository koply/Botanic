package me.koply.botanic.bot;

import me.koply.botanic.bot.command.records.Parameters;
import me.koply.botanic.data.records.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BotanicBot {

    private final Config config;
    public BotanicBot(Config config) {
        this.config = config;
    }

    public void start() {
        try {
            JDA jda = JDABuilder.createDefault(config.getToken())
                    .setAutoReconnect(true)
                    .build();
            jda.awaitReady();

            Parameters.getInstance().setOwners(config.getOwners())
                    .setCooldown(config.getCooldown())
                    .setJda(jda)
                    .setPrefix(config.getPrefix())
                    .setReadBotMessages(config.isReadBotMessages());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}