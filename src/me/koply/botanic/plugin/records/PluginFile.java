package me.koply.botanic.plugin.records;

import me.koply.botanic.util.LightYml;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginFile {

    public PluginFile(File file, JarFile jarFile, JarEntry pluginEntry, LightYml yaml) {
        this.file = file;
        this.jarFile = jarFile;
        this.pluginEntry = pluginEntry;
        yamlFile = yaml;
    }

    private final File file;
    private final JarFile jarFile;
    private final JarEntry pluginEntry;
    private final LightYml yamlFile;

    public File getFile() {
        return file;
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    public JarEntry getPluginEntry() {
        return pluginEntry;
    }

    public LightYml getYamlFile() {
        return yamlFile;
    }
}