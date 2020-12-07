package me.koply.botanic.bot;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bot.kcommando.CommandHandler;
import me.koply.botanic.bot.kcommando.KCommando;
import me.koply.botanic.bot.kcommando.Params;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.ArrayList;

public class BotanicBot {

    private final String token;
    private final Params params;
    private final ArrayList<BionicFile> bionicFiles;
    public BotanicBot(String token, Params params, ArrayList<BionicFile> bionicFiles) {
        this.token = token;
        this.params = params;
        this.bionicFiles = bionicFiles;
    }

    public void start() {
        try {
            // registers all commands
            final KCommando kcm = new KCommando(params);
            kcm.buildAllBionicCommands(bionicFiles);

            Main.LOGGER.info("------ Bot launching... ------");
            JDA jda = JDABuilder.createDefault(token)
                    .setAutoReconnect(true)
                    .build();
            jda.awaitReady();
            Main.LOGGER.info("------ Bot launched! ------");

            jda.addEventListener(new CommandHandler(params));
            kcm.buildAllBionicListeners(bionicFiles, jda);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}