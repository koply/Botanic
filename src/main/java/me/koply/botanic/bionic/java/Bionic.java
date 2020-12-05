package me.koply.botanic.bionic.java;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Bionic {
    private final ArrayList<ListenerAdapter> listeners = new ArrayList<>();
    public final ArrayList<ListenerAdapter> getListeners() { return listeners; }

    private Package commandsPackage;
    public final Bionic setCommandPackage(Package pack) {
        commandsPackage = pack;
        return this;
    }
    public final Package getCommandsPackage() {
        return commandsPackage;
    }

    public abstract void onEnable();
    public abstract void onDisable();

    public final void addListener(ListenerAdapter...adapters) {
        listeners.addAll(Arrays.asList(adapters));
    }

}