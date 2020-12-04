package me.koply.botanic.data.records;

public class Config {
    private String token, prefix;
    private long cooldown;
    private boolean readBotMessages;
    private String[] owners;

    public Config(String token, String prefix, long cooldown, boolean readBotMessages, String[] owners) {
        this.token = token;
        this.prefix = prefix;
        this.cooldown = cooldown;
        this.readBotMessages = readBotMessages;
        this.owners = owners;
    }

    public String getToken() {
        return token;
    }

    public Config setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Config setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public long getCooldown() {
        return cooldown;
    }

    public Config setCooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public boolean isReadBotMessages() {
        return readBotMessages;
    }

    public Config setReadBotMessages(boolean readBotMessages) {
        this.readBotMessages = readBotMessages;
        return this;
    }

    public String[] getOwners() {
        return owners;
    }

    public Config setOwners(String[] owners) {
        this.owners = owners;
        return this;
    }
}