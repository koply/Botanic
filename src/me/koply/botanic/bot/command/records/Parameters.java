package me.koply.botanic.bot.command.records;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parameters {

    private static Parameters instance;

    public static Parameters getInstance() {
        if (instance == null) instance = new Parameters();
        return instance;
    }

    private long cooldown = 0L;
    private boolean readBotMessages;
    private String prefix;
    private ArrayList<String> packagePaths = new ArrayList<>();
    private ArrayList<String> owners = new ArrayList<>();
    private HashMap<String, CommandToRun> commandMethods = new HashMap<>();

    public HashMap<String, CommandToRun> getCommandMethods() {
        return commandMethods;
    }

    public ArrayList<String> getPackagePaths() {
        return packagePaths;
    }

    public Parameters addPackagePath(String path) {
        packagePaths.add(path);
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
        this.owners.addAll(Arrays.asList(owners));
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