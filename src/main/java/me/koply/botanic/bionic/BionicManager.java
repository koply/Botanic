package me.koply.botanic.bionic;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.java.Bionic;
import me.koply.botanic.bot.command.records.Parameters;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.util.Gen;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class BionicManager {

    private final Logger logger;
    private final File folder;
    private final URLClassLoader loader;

    public BionicManager(File folder) throws IllegalArgumentException, MalformedURLException {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The file parameter must be a folder");
        }
        logger = Main.LOGGER;
        this.folder = folder;
        loader = new URLClassLoader(new URL[] {new URL("file:///"+folder.getPath()+"/")}, this.getClass().getClassLoader());
    }

    public final ArrayList<BionicFile> detectBionics() {
        /*
        loadClass(classname) -> ile class yüklenebiliyor
         */
        final ArrayList<BionicFile> bionics = new ArrayList<>();
        int i = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jar = new JarFile(file)) {

                    JarEntry jarEntry = jar.getJarEntry("bionic.gen");
                    if (jarEntry == null) {
                        logger.warning(file.getName() + " is couldn't have bionic.gen.");
                        continue;
                    }

                    Gen gen = new Gen(jar.getInputStream(jarEntry));
                    if (!gen.isOk()) {
                        logger.warning(file.getName() + "'s bionic.gen file contains syntax errors.");
                        continue;
                    }
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(gen.getAttributes().get("main"), true, loader);
                    } catch (ClassNotFoundException classNotFoundException) {
                        logger.warning("Main class named as " + gen.getAttributes().get("main") + " is not found in the " + file.getName());
                        continue;
                    }
                    i++;
                    bionics.add(new BionicFile(file, jar, jarEntry, gen, clazz));
                } catch (Exception ex) {
                    logger.warning("An error occurred while activating the " + file.getName());
                    ex.printStackTrace();
                }
            }
        }
        logger.info("Loaded " + i + " commands.");
        return bionics;
    }

    public final void enablePlugins(ArrayList<BionicFile> bionicFiles) {
        final Class<?> type = Bionic.class.getComponentType();
        for (BionicFile bionic : bionicFiles) {
            if (!type.isInstance(bionic.getMainClass())) {
                logger.warning(bionic.getFile().getName() + " could not be enabled. Main class is not extends Bionic.");
                bionicFiles.remove(bionic);
                continue;
            }

            Bionic instance;
            try {
                instance = (Bionic) bionic.getMainClass().newInstance();
            } catch (Exception e) {
                logger.warning(bionic.getFile().getName() + " could not be enabled.");
                bionicFiles.remove(bionic);
                continue;
            }
            instance.onEnable();
            bionic.setInstance(instance);
        }
    }

}