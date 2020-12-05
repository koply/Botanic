package me.koply.botanic.bionic.records;

import me.koply.botanic.bionic.java.Bionic;
import me.koply.botanic.bot.command.records.CommandToRun;
import me.koply.botanic.util.Gen;

import java.io.File;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BionicFile {

    public BionicFile(File file, JarFile jarFile, JarEntry pluginEntry, Gen gen, Class<?> mainClass) {
        this.file = file;
        this.jarFile = jarFile;
        this.pluginEntry = pluginEntry;
        this.gen = gen;
        this.mainClass = mainClass;
    }

    private final File file;
    private final JarFile jarFile;
    private final JarEntry pluginEntry;
    private final Gen gen;
    private final Class<?> mainClass;
    private Bionic instance;
    private HashSet<CommandToRun> commandToRuns = new HashSet<>();

    public File getFile() {
        return file;
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public JarEntry getPluginEntry() {
        return pluginEntry;
    }

    public Gen getGen() {
        return gen;
    }

    public Class<?> getMainClass() {
        return mainClass;
    }

    public BionicFile setInstance(Bionic instance) {
        this.instance = instance;
        return this;
    }

    public Bionic getInstance() {
        return instance;
    }

    public BionicFile addCommandToRun(CommandToRun commandToRun) {
        commandToRuns.add(commandToRun);
        return this;
    }

    public HashSet<CommandToRun> getCommandToRuns() {
        return commandToRuns;
    }
}