package me.koply.botanic.bot.command.records;

import java.util.*;

public class Parameters {

    private static Parameters instance;
    public static Parameters getInstance() {
        if (instance == null) instance = new Parameters();
        return instance;
    }

    private long cooldown = 0L;
    private boolean readBotMessages;
    private String prefix;
    private final ArrayList<String> packagePaths = new ArrayList<>();
    private final HashSet<String> owners = new HashSet<>();
    private final HashMap<String, CommandToRun> commandClasses = new HashMap<>();

    public HashMap<String, CommandToRun> getCommandClasses() {
        return commandClasses;
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

    public HashSet<String> getOwners() {
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