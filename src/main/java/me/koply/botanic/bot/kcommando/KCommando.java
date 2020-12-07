package me.koply.botanic.bot.kcommando;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bot.kcommando.enums.CommandType;
import me.koply.botanic.bot.kcommando.internal.Commander;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

// signed by Koply
public final class KCommando {

    private final Params params;
    public static final Logger logger = Main.LOGGER;
    public static final String VERSION = "Forked-3.0";

    public KCommando(Params params) {
        this.params = params;
    }

    public void buildAllBionicCommands(ArrayList<BionicFile> bionicFiles) {
        logger.info("The commands of the bionics are initializing...");
        for (BionicFile file : bionicFiles) {
            build(file.getInstance().getCommands(), file.getGen().getAttributes().get("name"));
        }
        logger.info("The commands of the bionics are initialized!");
    }

    public void buildAllBionicListeners(ArrayList<BionicFile> bionicFiles, JDA jda) {
        logger.info("The events of the bionics are initializing...");
        for (BionicFile bionic : bionicFiles) {
            ArrayList<ListenerAdapter> listenersList = bionic.getInstance().getListeners();
            ListenerAdapter[] listeners = new ListenerAdapter[listenersList.size()];
            int i = 0;
            for (ListenerAdapter listener : listenersList) {
                listeners[i] = listener;
                i++;
            }
            jda.addEventListener(listeners);
        }
        logger.info("The events of the bionics are initialized!");
    }

    private void build(ArrayList<Command> classes, String bionicName) {
        int classCounter = 0;
        final HashMap<String, CommandToRun> commandMethods = new HashMap<>();
        for (Command clazzInstance : classes) {
            final Class<? extends Command> clazz = clazzInstance.getClass();

            final Commander commandAnnotation = clazz.getAnnotation(Commander.class);
            if (commandAnnotation == null) {
                KCommando.logger.warning(clazz.getName() + " is couldn't have Command annotation. Skipping...");
                continue;
            }
            if ((clazz.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
                KCommando.logger.warning(clazz.getName() + " is not public class. Skipping...");
                continue;
            }
            int methodCounter = 0;
            CommandType type = null;

            if (commandAnnotation.guildOnly() && commandAnnotation.privateOnly()) {
                KCommando.logger.warning(clazz.getName() + " is have GuildOnly and PrivateOnly at the same time. Skipping...");
                continue;
            }

            for (Method metod : clazz.getDeclaredMethods()) {
                Class<?>[] parameters = metod.getParameterTypes();
                if (parameters.length <= 3 && parameters.length != 0 && metod.getName().equals("handle")) {
                    if (parameters.length == 1) {
                        if (parameters[0] == MessageReceivedEvent.class) {
                            methodCounter++;
                            type = CommandType.EVENT;
                        }
                    } else if (parameters[0] == MessageReceivedEvent.class && parameters[1].isArray()) { // ??
                        methodCounter++;
                        type = CommandType.ARGNEVENT;
                    }
                }
            }

            if (methodCounter > 1) {
                KCommando.logger.warning(clazz.getName() + " is have multiple command method. Skipping...");
                continue;
            }

            final String[] packageSplitted = clazz.getPackage().getName().split("\\.");
            final String groupName = packageSplitted[packageSplitted.length-1];

            try {
                CommandInfo tempinfo = new CommandInfo();
                tempinfo.initialize(commandAnnotation);
                CargoTruck.setCargo(tempinfo);

                final Command commandInstance = clazz.getDeclaredConstructor().newInstance();
                final CommandToRun ctr = new CommandToRun(commandInstance, groupName, type);

                for (final String s : commandAnnotation.aliases()) {
                    final String name = params.getCaseSensitivity().map(s::toLowerCase).orElse(s);
                    commandMethods.put(name, ctr);
                }
                classCounter++;

            } catch (Throwable t) {
                KCommando.logger.warning("Something went wrong.");
            }
        }
        CargoTruck.setCargo(null);
        params.addCommandMethods(commandMethods);
        KCommando.logger.info(classCounter + " commands are initialized from " + bionicName);
    }

    public static final class CargoTruck {
        public static CommandInfo cargo;
        public static void setCargo(CommandInfo cargo1) {
            cargo = cargo1;
        }
        public static CommandInfo getCargo() {
            return cargo;
        }
    }

    // setters
    public Params getParams() {
        return params;
    }
}
