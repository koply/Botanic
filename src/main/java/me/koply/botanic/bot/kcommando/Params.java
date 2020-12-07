package me.koply.botanic.bot.kcommando;

import java.util.*;

public final class Params {
    private String prefix;
    private long cooldown = 0L;
    private HashMap<String, String> groupLocales;
    private final HashSet<String> owners = new HashSet<>();
    private final HashMap<String, CommandToRun> commandMethods = new HashMap<>();
    private boolean readBotMessages;
    private Optional<Locale> caseSensitivity = Optional.empty();

    public final HashMap<String, CommandToRun> getCommandMethods() {
        return commandMethods;
    }

    public final void addCommandMethods(HashMap<String, CommandToRun> commandMethods) {
        this.commandMethods.putAll(commandMethods);
    }

    public final String getPrefix() {
        return prefix;
    }

    public final Params setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public final long getCooldown() {
        return cooldown;
    }

    public final Params setCooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public final HashMap<String, String> getGroupLocales() {
        return groupLocales;
    }

    public final Params setGroupLocales(HashMap<String, String> groupLocales) {
        this.groupLocales = groupLocales;
        return this;
    }

    public final HashSet<String> getOwners() {
        return owners;
    }

    public final Params setOwners(String[] owners) {
        this.owners.addAll(Arrays.asList(owners));
        return this;
    }

    public final boolean isReadBotMessages() {
        return readBotMessages;
    }

    public final Params setReadBotMessages(boolean readBotMessages) {
        this.readBotMessages = readBotMessages;
        return this;
    }

    public Optional<Locale> getCaseSensitivity() { return caseSensitivity; }

    public final void setCaseSensitivity(Locale cs) {
        caseSensitivity = Optional.ofNullable(cs);
    }

}