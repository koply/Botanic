package me.koply.botanic.bot;

import me.koply.kcommando.integration.impl.jda.JDAIntegration;
import net.dv8tion.jda.api.JDA;

public class BotRunner extends JDAIntegration {

    public BotRunner(JDA jda) {
        super(jda);
    }
}