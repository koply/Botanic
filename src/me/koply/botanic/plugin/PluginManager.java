package me.koply.botanic.plugin;

import me.koply.botanic.bot.command.records.Parameters;
import me.koply.botanic.plugin.records.PluginFile;
import me.koply.botanic.util.Gen;

import java.io.File;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class PluginManager {

    private final Logger logger;
    public PluginManager() {
        logger = Logger.getLogger("PluginManager");
    }

    public ArrayList<PluginFile> detectPlugins(File folder) {
        final ArrayList<PluginFile> plugins = new ArrayList<>();
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The file parameter must be a folder.");
        }
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jar = new JarFile(file)) {

                    JarEntry jarEntry = jar.getJarEntry("plugin.yml");
                    if (jarEntry == null) {
                        logger.warning(file.getName() + " is couldn't have bionic.gen.");
                        continue;
                    }

                    Gen gen = new Gen(jar.getInputStream(jarEntry));
                    if (!gen.isOk()) {
                        logger.warning(file.getName() + "'s bionic.gen file contains syntax errors.");
                        continue;
                    }

                    plugins.add(new PluginFile(file, jar, jarEntry, gen));

                } catch (Exception ex) {
                    logger.warning("An error occurred while activating the " + file.getName());
                    ex.printStackTrace();
                }
            }
        }
        return plugins;
    }

    /*
    URLClassLoader loader = new URLClassLoader(new URL[] {jar url'leri}, this.getClass().getClassLoader());
        Class<?> clazz = loader.findClass("isim");
     */
    public void enablePlugins(ArrayList<PluginFile> pluginFiles, Parameters params) {

    }

}