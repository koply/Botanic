package me.koply.botanic.plugin.java;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BotanicPlugin {
    private final ArrayList<ListenerAdapter> listeners = new ArrayList<>();
    public ArrayList<ListenerAdapter> getListeners() { return listeners; }

    private BotanicPlugin() {

    }

    abstract void onEnable();
    abstract void onDisable();

    public void registerListeners(ListenerAdapter...adapters) {
        listeners.addAll(Arrays.asList(adapters));
    }



}