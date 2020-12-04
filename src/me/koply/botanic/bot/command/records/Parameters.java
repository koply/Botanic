package me.koply.botanic.bot.command.records;

import net.dv8tion.jda.api.JDA;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parameters {

    private static Parameters instance;

    public static Parameters getInstance() {
        if (instance == null) instance = new Parameters();
        return instance;
    }

    private JDA jda;
    private String packagePath, prefix;
    private long cooldown = 0L;
    private List<String> owners;
    private HashMap<String, CommandToRun> commandMethods;
    private boolean readBotMessages;

    public HashMap<String, CommandToRun> getCommandMethods() {
        return commandMethods;
    }

    public Parameters setCommandMethods(HashMap<String, CommandToRun> commandMethods) {
        this.commandMethods = commandMethods;
        return this;
    }

    public JDA getJda() {
        return jda;
    }

    public Parameters setJda(JDA jda) {
        this.jda = jda;
        return this;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public Parameters setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Parameters setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public long getCooldown() {
        return cooldown;
    }

    public Parameters setCooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public List<String> getOwners() {
        return owners;
    }

    public Parameters setOwners(String[] owners) {
        this.owners = Arrays.asList(owners);
        return this;
    }

    public boolean isReadBotMessages() {
        return readBotMessages;
    }

    public Parameters setReadBotMessages(boolean readBotMessages) {
        this.readBotMessages = readBotMessages;
        return this;
    }
}