package me.koply.botanic.bot.kcommando;

import me.koply.botanic.bot.kcommando.internal.ICommand;

public abstract class Command implements ICommand {

    private final CommandInfo info;
    public Command() {
        info = KCommando.CargoTruck.getCargo();
    }
    public final CommandInfo getInfo() { return info; }
    


}