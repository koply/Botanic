package me.koply.botanic.bionic.java;

import me.koply.botanic.bionic.BionicInfo;
import me.koply.botanic.bionic.CargoTruck;
import me.koply.botanic.bot.kcommando.Command;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class Bionic {

    protected final BionicInfo info;
    public final BionicInfo getInfo() { return info; }

    public final Logger getLogger()  { return info.getLogger(); }
    public final String getName() { return info.getName(); }
    public final File getDataFolder() { return info.getDataFolder(); }

    public Bionic() {
        info = CargoTruck.getDelivery();
    }

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