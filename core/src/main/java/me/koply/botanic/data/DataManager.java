package me.koply.botanic.data;

import me.koply.botanic.Main;
import me.koply.botanic.data.records.Config;
import me.koply.botanic.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public final class DataManager {

    // instance
    private static DataManager instance;
    public static DataManager getInstance() { if (instance == null) instance = new DataManager();
        return instance;
    }
    // end instance

    private final File config = new File("config.json");
    private final String configTemplate;

    public DataManager() {
        configTemplate = Util.readFile(getClass().getClassLoader().getResourceAsStream("config.template"));
        if (config.exists()) return;
        try {
            if (config.createNewFile()) {
                Main.LOGGER.info(config.getName() + " file created from template.");
                Util.writeFile(config, configTemplate);
                Main.LOGGER.warning("You should fill the config.json");
                System.exit(-1);
            }
        } catch (Throwable t) { t.printStackTrace();
            Main.LOGGER.warning(config.getName() + " file could not be created due to an error");
        }
    }

    public Config readConfig() {
        final String configStr = Util.readFile(config);
        if (configStr.isEmpty()) {
            Main.LOGGER.warning("Config file not in proper format.");
            Main.LOGGER.warning("Config file filling...");
            Util.writeFile(config, configTemplate);
            Main.LOGGER.warning("Config file filled!");
            System.exit(-1);
        }
        try {
            JSONObject tempconfig = new JSONObject(configStr);
            Main.LOGGER.info("Config file readed.");

            JSONArray ownersArray = tempconfig.getJSONArray("owners");
            String[] owners = new String[ownersArray.length()];
            for (int i = 0; i < ownersArray.length(); i++) {
                owners[i] = ownersArray.get(i).toString();
            }

            return new Config(tempconfig.getString("token"), tempconfig.getString("prefix"),
                    tempconfig.getLong("cooldown"), tempconfig.getBoolean("readBotMessages"),
                    owners, tempconfig.getBoolean("caseSensitivity"));
        } catch (Throwable t) {
            t.printStackTrace();
            Main.LOGGER.warning("file could not be readed due to an error.");
            System.exit(-1);
            return null;
        }
    }

}