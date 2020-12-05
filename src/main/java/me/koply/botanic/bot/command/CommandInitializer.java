package me.koply.botanic.bot.command;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bot.command.annotation.Command;
import me.koply.botanic.bot.command.records.CommandToRun;
import me.koply.botanic.bot.command.records.Parameters;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.reflections8.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public final class CommandInitializer {

    private final Parameters params;
    private final Logger logger;
    private final ArrayList<BionicFile> bionics;

    public CommandInitializer(ArrayList<BionicFile> bionicFiles) {
        params = Parameters.getInstance();
        logger = Main.LOGGER;
        bionics = bionicFiles;
    }

    public void initialize() {
        int classCounter = 0;
        int bionicCounter = 0;
        final HashMap<String, CommandToRun> commandsMap = params.getCommandClasses();
        for (BionicFile bionic : bionics) {

            final Reflections reflections = new Reflections(bionic.getInstance().getCommandsPackage(),
                    bionic.getInstance().getClass().getClassLoader());
            Set<Class<? extends CommandUtils>> classes = reflections.getSubTypesOf(CommandUtils.class);
            logger.info(classes.size() +"");
            for (Class<? extends CommandUtils> clazz : classes) {
                Command cmdAnnotation = clazz.getAnnotation(Command.class);
                if (cmdAnnotation == null) {
                    logger.warning(clazz.getName() + " is couldn't have Command annotation. Skipping...");
                    continue;
                }
                if ((clazz.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) {
                    logger.warning(clazz.getName() + " is not public class. Skipping...");
                    continue;
                }
                int methodCounter = 0;
                CommandUtils.TYPE type = null;

                if (cmdAnnotation.guildOnly() && cmdAnnotation.privateOnly()) {
                    logger.warning(clazz.getName() + " is have GuildOnly and PrivateOnly at the same time. Skipping...");
                    continue;
                }

                for (Method metod : clazz.getDeclaredMethods()) {
                    Class<?>[] parameters = metod.getParameterTypes();
                    if (parameters.length <= 3 && parameters.length != 0 && metod.getName().equals("handle")) {
                        if (parameters.length == 1) {
                            if (parameters[0] == MessageReceivedEvent.class) {
                                methodCounter++;
                                type = CommandUtils.TYPE.EVENT;
                            }
                        } else if (parameters[0] == MessageReceivedEvent.class && parameters[1] == Parameters.class) {
                            methodCounter++;
                            type = CommandUtils.TYPE.PARAMETEREDEVENT;
                        } else if (parameters[0] == MessageReceivedEvent.class && parameters[1].isArray()) { // ??
                            methodCounter++;
                            type = CommandUtils.TYPE.ARGNEVENT;
                        }
                    }
                }

                if (methodCounter > 1) {
                    logger.warning(clazz.getName() + " is have multiple command method. Skipping...");
                    continue;
                }

                String[] packageSplitted = clazz.getPackage().getName().split("\\.");
                String groupName = packageSplitted[packageSplitted.length-1];

                try {
                    CommandToRun ctr = new CommandToRun()
                            .setClazz(clazz.newInstance())
                            .setCommandAnnotation(cmdAnnotation)
                            .setType(type)
                            .setGroupName(groupName);

                    for (String s : cmdAnnotation.names()) {
                        commandsMap.put(s, ctr);
                    }
                    classCounter++;

                } catch (Throwable t) {
                    logger.warning("Something went wrong.");
                } finally {
                    logger.info(clazz.getName() + " is have command method");
                }

            }
            bionicCounter++;
        }

        logger.info(bionicCounter + " bionic and " + classCounter + " commands registered.");
    }

}