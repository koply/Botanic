package me.koply.fbionic;

import me.koply.botanic.bionic.java.Bionic;

public class Main extends Bionic {

    public Main() {
        System.out.println("Called the first bionics constructor!");
    }

    @Override
    public void onEnable() {
        System.out.println("The first bionic is enabled!");
        setCommandPackage(Main.class.getPackage());
    }

    @Override
    public void onDisable() {
    }
}