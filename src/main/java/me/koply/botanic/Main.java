package me.koply.botanic;

import me.koply.botanic.bionic.BionicManager;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bot.BotanicBot;
import me.koply.botanic.bot.kcommando.Params;
import me.koply.botanic.data.DataManager;
import me.koply.botanic.data.records.Config;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {
    public static Logger LOGGER = Logger.getLogger("Botanic");
    public static Config config;
    public static BionicManager bionicManager;
    private final File bionicsFolder = new File("bionics/");

    static {
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            public String format(LogRecord record) {
                String[] splitted = record.getSourceClassName().split("\\.");
                return String.format("[%s %s] %s -> %s\n", this.formatter.format(new Date(record.getMillis())), record.getLevel(), splitted[splitted.length-1], record.getMessage());
            }
        });
        LOGGER.addHandler(consoleHandler);
    }

    private Main() {
        createBionicsFolder();
        config = DataManager.getInstance().readConfig();
        Params params = new Params().setOwners(config.getOwners())
                .setCooldown(config.getCooldown())
                .setPrefix(config.getPrefix())
                .setReadBotMessages(config.isReadBotMessages());

        if (config.isCaseSensitivity()) {
            params.setCaseSensitivity(Locale.getDefault());
        }

        LOGGER.info("BionicManager running...");
        bionicManager = new BionicManager(bionicsFolder);

        final ArrayList<BionicFile> bionicFiles = bionicManager.detectBionics();
        bionicManager.enableBionics(bionicFiles);

        BotanicBot botanicBot = new BotanicBot(config.getToken(), params, bionicFiles);
        botanicBot.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void createBionicsFolder() {
        if (!bionicsFolder.exists()) {
            bionicsFolder.mkdir();
        }
    }
}
