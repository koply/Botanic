package me.koply.botanic.plugin;

import me.koply.botanic.plugin.records.PluginFile;

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

    private final ArrayList<PluginFile> plugins = new ArrayList<>();

    public void detectPlugins(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The file parameter must be a folder.");
        }
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jar = new JarFile(file)) {

                    JarEntry jarEntry = jar.getJarEntry("plugin.yml");
                    if (jarEntry == null) {
                        logger.warning(file.getName() + " is couldn't have plugin.yml.");
                        continue;
                    }



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}