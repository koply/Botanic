package me.koply.botanic.plugin.records;

import me.koply.botanic.util.Gen;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginFile {

    public PluginFile(File file, JarFile jarFile, JarEntry pluginEntry, Gen gen) {
        this.file = file;
        this.jarFile = jarFile;
        this.pluginEntry = pluginEntry;
        this.gen = gen;
    }

    private final File file;
    private final JarFile jarFile;
    private final JarEntry pluginEntry;
    private final Gen gen;

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
}