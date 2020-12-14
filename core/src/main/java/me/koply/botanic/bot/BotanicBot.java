package me.koply.botanic.bot;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.kcommando.KCommando;
import me.koply.kcommando.Parameters;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class BotanicBot {

    private final String token;
    private final Parameters params;
    private static ArrayList<BionicFile> bionicFiles;
    public static ArrayList<BionicFile> getBionicFiles() { return new ArrayList<>(bionicFiles); }

    public BotanicBot(String token, Parameters params, ArrayList<BionicFile> bionicFiles1) {
        this.token = token;
        this.params = params;
        bionicFiles = bionicFiles1;
    }

    private KCommando kcm;

    public void start() {
        try {
            // registers all commands
            Main.LOGGER.info("------ Bot launching... ------");
            JDA jda = JDABuilder.createDefault(token)
                    .setAutoReconnect(true)
                    .build();
            jda.awaitReady();
            Main.LOGGER.info("------ Bot launched! ------");

            BotRunner integration = new BotRunner(jda);
            params.setIntegration(integration);

            kcm = new KCommando(integration, new CustomInitializer(params));
            kcm.build();

            for (BionicFile bionic : bionicFiles) {
                for (ListenerAdapter listeners : bionic.getInstance().getListeners()) {
                    jda.addEventListener(listeners);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}