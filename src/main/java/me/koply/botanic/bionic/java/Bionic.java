package me.koply.botanic.bionic.java;

import me.koply.botanic.bot.kcommando.Command;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Bionic {
    private final ArrayList<ListenerAdapter> listeners = new ArrayList<>();
    public final ArrayList<ListenerAdapter> getListeners() { return listeners; }
    public final void addListener(ListenerAdapter...adapters) {
        listeners.addAll(Arrays.asList(adapters));
    }

    private final ArrayList<Command> commands = new ArrayList<>();
    public final ArrayList<Command> getCommands() { return commands; }
    public final void addCommand(Command...coms) { commands.addAll(Arrays.asList(coms)); }

    public abstract void onEnable();
    public abstract void onDisable();
}