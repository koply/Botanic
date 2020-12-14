package me.koply.botanic.bot;

import me.koply.botanic.bionic.records.BionicFile;
import me.koply.kcommando.Command;
import me.koply.kcommando.KInitializer;
import me.koply.kcommando.Parameters;
import me.koply.kcommando.integration.impl.jda.JDACommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class CustomInitializer extends KInitializer {

    public CustomInitializer(Parameters params) {
        super(params);
    }

    @Override
    public final Set<Class<? extends Command>> getCommands() {
        ArrayList<BionicFile> bionics = BotanicBot.getBionicFiles();
        Set<Class<? extends Command>> commands = new HashSet<>();
        for (BionicFile bionic : bionics) {
            for (JDACommand jdacmd : bionic.getInstance().getCommands()) {
                commands.add(jdacmd.getClass());
            }
        }
        return commands;
    }
}