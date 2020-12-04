package me.koply.botanic.plugin.records;

import me.koply.botanic.plugin.java.BotanicPlugin;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginFile {

    public PluginFile(File file, JarFile jarFile, JarEntry pluginEntry, BotanicPlugin main) {
        this.file = file;
        this.jarFile = jarFile;
        this.pluginEntry = pluginEntry;
        this.main = main;
    }

    private final File file;
    private final JarFile jarFile;
    private final JarEntry pluginEntry;
    private final BotanicPlugin main;

    public File getFile() {
        return file;
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public JarEntry getPluginEntry() {
        return pluginEntry;
    }

    public BotanicPlugin getMain() {
        return main;
    }
}