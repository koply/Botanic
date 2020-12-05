package me.koply.botanic.bionic;

import me.koply.botanic.Main;
import me.koply.botanic.bionic.java.Bionic;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.util.Gen;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class BionicManager {

    private final Logger logger;
    private final File folder;

    public BionicManager(File folder) throws IllegalArgumentException {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The file parameter must be a folder");
        }
        logger = Main.LOGGER;
        this.folder = folder;
    }

    public final ArrayList<BionicFile> detectBionics() {
        final ArrayList<BionicFile> bionics = new ArrayList<>();
        final ArrayList<URL> urlArray = new ArrayList<>();

        for (File file : folder.listFiles()) {
            if (!file.isFile() && !file.getName().endsWith(".jar")) continue;

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
                bionics.add(new BionicFile(file, jar, jarEntry, gen));
                urlArray.add(file.toURI().toURL());
            } catch (Exception ex) {
                logger.warning("An error occurred while loading the " + file.getName());
                ex.printStackTrace();
            }
        }

        final URL[] urls = new URL[urlArray.size()];
        for (int j = 0; j < urlArray.size(); j++) {
            urls[j] = urlArray.get(j);
        }
        final URLClassLoader loader = new URLClassLoader(urls, this.getClass().getClassLoader());

        for (BionicFile bionic : bionics) {
            try {
                Class<?> clazz = Class.forName(bionic.getGen().getAttributes().get("main"), true, loader);
                bionic.setMainClass(clazz);
            } catch (ClassNotFoundException classNotFoundException) {
                logger.warning("Main class named as " + bionic.getGen().getAttributes().get("main") + " is not found in the " + bionic.getFile().getName());
            }
        }

        return bionics;
    }

    public final void enableBionics(ArrayList<BionicFile> bionicFiles) {
        final ArrayList<BionicFile> toremove = new ArrayList<>();
        for (BionicFile bionic : bionicFiles) {
            if (bionic.getMainClass().getSuperclass() != Bionic.class) {
                logger.warning(bionic.getFile().getName() + " could not be enabled. Main class is not extends Bionic.");
                toremove.add(bionic);
                continue;
            }

            try {
                Bionic instance = (Bionic) bionic.getMainClass().newInstance();
                instance.onEnable();
                bionic.setInstance(instance);
            } catch (Exception e) {
                logger.warning(bionic.getFile().getName() + " could not be enabled.");
                toremove.add(bionic);
            }
        }

        for (BionicFile b : toremove) {
            bionicFiles.remove(b);
        }
    }

}