package me.koply.botanic.data.records;

public final class Config {
    private final String token, prefix;
    private final long cooldown;
    private final boolean readBotMessages, caseSensitivity;
    private final String[] owners;

    public Config(String token, String prefix, long cooldown, boolean readBotMessages, String[] owners, boolean caseSensitivity) {
        this.token = token;
        this.prefix = prefix;
        this.cooldown = cooldown;
        this.readBotMessages = readBotMessages;
        this.owners = owners;
        this.caseSensitivity = caseSensitivity;
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isReadBotMessages() {
        return readBotMessages;
    }

    public String[] getOwners() {
        return owners;
    }

    public boolean isCaseSensitivity() {
        return caseSensitivity;
    }

}