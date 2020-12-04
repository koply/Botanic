package me.koply.botanic.bot;

import me.koply.botanic.bot.commands.CPing;
import me.koply.botanic.data.records.Config;
import me.koply.kcommando.KCommando;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BotanicBot extends Thread {

    private final Config config;
    public BotanicBot(Config config) {
        this.config = config;
    }

    @Override
    public void start() {
        try {
            JDA jda = JDABuilder.createDefault(config.getToken())
                    .setAutoReconnect(true)
                    .build();
            jda.awaitReady();

            KCommando kcm = new KCommando(jda)
                    .setCooldown(config.getCooldown())
                    .setOwners(config.getOwners())
                    .setPrefix(config.getPrefix())
                    .setPackage(CPing.class.getPackage().getName())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}