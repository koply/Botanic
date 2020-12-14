package me.koply.botanic.bionic.records;

import me.koply.botanic.bionic.java.Bionic;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class BionicFile {

    public BionicFile(File file, JarFile jarFile, JarEntry pluginEntry, Gen gen) {
        this.file = file;
        this.jarFile = jarFile;
        this.pluginEntry = pluginEntry;
        this.gen = gen;
    }

    private final File file;
    private final JarFile jarFile;
    private final JarEntry pluginEntry;
    private final Gen gen;
    private Class<?> mainClass;
    private Bionic instance;


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

    public BionicFile setMainClass(Class<?> mainClass) {
        this.mainClass = mainClass;
        return this;
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

}