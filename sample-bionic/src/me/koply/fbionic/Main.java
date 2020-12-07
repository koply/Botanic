package me.koply.fbionic;

import me.koply.botanic.bionic.java.Bionic;

public class Main extends Bionic {

    public Main() {
        // you cant use parametered main constructor in your bionic
        System.out.println("Called the first bionics constructor!");
    }

    @Override
    public void onEnable() {
        System.out.println("The first bionic is enabled!");
        addCommand(new PingCommand());
        addListener(new BasicEvent());
    }

    @Override
    public void onDisable() {
    }
}