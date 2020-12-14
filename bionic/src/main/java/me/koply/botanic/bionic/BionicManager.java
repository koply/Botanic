package me.koply.botanic.bionic;

import me.koply.botanic.bionic.java.Bionic;
import me.koply.botanic.bionic.records.BionicFile;
import me.koply.botanic.bionic.records.Gen;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class BionicManager {

    private final Logger logger;
    private final File bionicsFolder;

    public BionicManager(File folder) throws IllegalArgumentException {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The file parameter must be a folder");
        }
        logger = Logger.getLogger("BionicManager");
        logger.info("Bionics folder found.");
        this.bionicsFolder = folder;
    }

    public final ArrayList<BionicFile> detectBionics() {
        logger.info("Bionics are detecting...");
        final ArrayList<BionicFile> bionics = new ArrayList<>();
        final ArrayList<URL> urlArray = new ArrayList<>();

        for (File file : bionicsFolder.listFiles()) {
            if (!file.isFile() && !file.getName().endsWith(".jar")) continue;

            try (JarFile jar = new JarFile(file)) {

                JarEntry jarEntry = jar.getJarEntry("bionic.gen");
                if (jarEntry == null) {
                    logger.warning(file.getName() + "'s bionic.gen file was not found.");
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
                final Class<?> clazz = Class.forName(bionic.getGen().getAttributes().get("main"), true, loader);
                logger.info("Main class successfully found at " + bionic.getGen().getAttributes().get("name"));
                bionic.setMainClass(clazz);
            } catch (ClassNotFoundException classNotFoundException) {
                logger.warning("Main class named as " + bionic.getGen().getAttributes().get("main") + " is not found in the " + bionic.getFile().getName());
            }
        }

        return bionics;
    }

    public final void enableBionics(ArrayList<BionicFile> bionicFiles) {
        logger.info("Detected bionics are enabling...");
        final ArrayList<BionicFile> toremove = new ArrayList<>();
        for (BionicFile bionic : bionicFiles) {
            final String bionicName = bionic.getGen().getAttributes().get("name");
            if (bionic.getMainClass().getSuperclass() != Bionic.class) {
                logger.warning(bionicName + " could not be enabled. Main class is not extends Bionic.");
                toremove.add(bionic);
                continue;
            }

            try {
                final File dataFolder = new File(bionicsFolder.getPath() + "/" + bionicName + "/");
                dataFolder.mkdir();
                final BionicInfo info = new BionicInfo(dataFolder, bionicName, getLogger(bionicName));
                CargoTruck.setDelivery(info);

                logger.info(bionicName + "'s constructor calling...");
                Bionic instance = (Bionic) bionic.getMainClass().getDeclaredConstructor().newInstance();
                logger.info(bionicName + " enabling...");
                instance.onEnable();
                logger.info(bionicName + " is enabled!");
                bionic.setInstance(instance);
            } catch (Exception e) {
                logger.warning(bionicName + " could not be enabled.");
                toremove.add(bionic);
            }
        }

        for (BionicFile b : toremove) {
            bionicFiles.remove(b);
        }
    }


    private Logger getLogger(String name) {
        final Logger logger = Logger.getLogger(name);

        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            public String format(LogRecord record) {
                return String.format("[%s %s] %s -> %s\n", this.formatter.format(new Date(record.getMillis())), record.getLevel(), record.getLoggerName(), record.getMessage());
            }
        });
        logger.addHandler(consoleHandler);

        return logger;
    }

}