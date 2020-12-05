package me.koply.botanic.bot.command;

import me.koply.botanic.Main;
import me.koply.botanic.bot.command.records.CommandToRun;
import me.koply.botanic.bot.command.records.Parameters;
import me.koply.botanic.util.CronService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public final class CommandHandler extends ListenerAdapter {

    private final Logger logger;
    private final Parameters params;
    private final HashMap<String, CommandToRun> commandsMap;
    private final ConcurrentMap<Long, Long> cooldownList = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public CommandHandler(Parameters params) {
        this.params = params;
        commandsMap = params.getCommandClasses();
        logger = Main.LOGGER;

        CronService.getInstance().addRunnable(() -> {
            long current = System.currentTimeMillis();
            int i = 0;
            for (Map.Entry<Long, Long> entry : cooldownList.entrySet()) {
                if (current - entry.getValue() >= params.getCooldown()) {
                    cooldownList.remove(entry.getKey());
                    i++;
                }
            }
            if (i == 0) return;
            logger.info("#CooldownList cleaned. " + i + " entries deleted.");

        });

        logger.info("[KCommando] CommandHandler initialized.");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if (e.getAuthor().getId().equals(e.getJDA().getSelfUser().getId())) return;

        if (!params.isReadBotMessages() && e.getAuthor().isBot()) return;

        if (e.isWebhookMessage()) return;

        String commandRaw = e.getMessage().getContentRaw();
        if (!commandRaw.startsWith(params.getPrefix())) return;

        String guildName = e.isFromGuild() ? e.getGuild().getName() : "(PRIVATE)";

        String[] cmdArgs = commandRaw.substring(params.getPrefix().length()).split(" ");
        logger.info(String.format("Command received | User: %s | Guild: %s | Command: %s", e.getAuthor().getAsTag(), guildName, commandRaw));

        if (!commandsMap.containsKey(cmdArgs[0])) {
            logger.info("Last command was not a valid command.");
            return;
        }

        CommandToRun ctr = commandsMap.get(cmdArgs[0]);
        if (ctr.getCommandAnnotation().guildOnly() && !e.isFromGuild()) {
            logger.info("GuildOnly command used from private channel");
            return;
        }
        if (ctr.getCommandAnnotation().privateOnly() && e.isFromGuild()) {
            logger.info("PrivateOnly command used from guild channel");
            return;
        }

        long authorID = e.getAuthor().getIdLong();
        if (ctr.getCommandAnnotation().ownerOnly() && !params.getOwners().contains(authorID + "")) {
            logger.info("OwnerOnly command used by normal user.");
            return;
        }

        if (cooldownCheck(authorID, cooldownList, params.getCooldown()) && !params.getOwners().contains(authorID + "")) {
            logger.info("Last command has been declined due to cooldown check");
            return;
        }

        long firstTime = System.currentTimeMillis();
        cooldownList.put(authorID, firstTime);
        if (ctr.getCommandAnnotation().sync()) {
            run(ctr, e, cmdArgs);
            logger.info("Last command took " + (System.currentTimeMillis() - firstTime) + "ms to execute.");
        } else {
            logger.info("Last command has been submitted to ExecutorService.");
            try {
                executorService.submit(() -> {
                    run(ctr, e, cmdArgs);
                    logger.info("Last command took " + (System.currentTimeMillis() - firstTime) + "ms to execute.");
                });
            } catch (Throwable t) { t.printStackTrace(); }
        }
    }

    private void run(CommandToRun ctr, MessageReceivedEvent e, String[] args) {
        try {
            if (ctr.getType() == CommandUtils.TYPE.PARAMETEREDEVENT) {
                ctr.getClazz().handle(e, params);
            } else if (ctr.getType() == CommandUtils.TYPE.ARGNEVENT) {
                ctr.getClazz().handle(e, args);
            } else {
                ctr.getClazz().handle(e);
            }
        }
        catch (Throwable t) { logger.info("Command crashed! Message: " + Arrays.toString(t.getStackTrace())); }
    }

    private boolean cooldownCheck(long userID, ConcurrentMap<Long, Long> cooldownList, long cooldown) {
        long listTime = cooldownList.getOrDefault(userID, 0L);
        return listTime != 0 && System.currentTimeMillis() - listTime <= cooldown;
    }
}